package uniandes.edu.co.proyecto.services;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.entities.enums.NivelVehiculo;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.*;
import uniandes.edu.co.proyecto.util.Geo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class SolicitudServicioTxService {

    private final ServicioRepository servicioRepo;
    private final UsuarioServicioRepository clienteRepo;
    private final PuntoGeograficoRepository puntoRepo;
    private final DisponibilidadRepository dispRepo;
    private final PagoService pagoService;

    public SolicitudServicioTxService(ServicioRepository servicioRepo,
                                      UsuarioServicioRepository clienteRepo,
                                      PuntoGeograficoRepository puntoRepo,
                                      DisponibilidadRepository dispRepo,
                                      PagoService pagoService) {
        this.servicioRepo = servicioRepo;
        this.clienteRepo = clienteRepo;
        this.puntoRepo = puntoRepo;
        this.dispRepo = dispRepo;
        this.pagoService = pagoService;
    }

    /**
     * RF8 transaccional: verifica pago, selecciona y bloquea disponibilidad,
     * cobra y registra el inicio. Si algo falla -> rollback.
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public ServicioEntity solicitarServicio(Long clienteId,
                                            String tipoServicio,
                                            Long origenId,
                                            Long destinoId,
                                            int tarifaKm,
                                            int longitudKm,
                                            LocalDate fecha,
                                            LocalTime horaDeseada,
                                            NivelVehiculo nivelOpcional) {

        // --- 1) Validaciones y carga ---
        if (clienteId == null || origenId == null || destinoId == null)
            throw new BusinessException("Cliente, origen y destino son obligatorios");
        if (tipoServicio == null || tipoServicio.isBlank())
            throw new BusinessException("Tipo de servicio es obligatorio");
        if (fecha == null || horaDeseada == null)
            throw new BusinessException("Fecha y hora son obligatorias");

        var cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        var origen = puntoRepo.findById(origenId)
                .orElseThrow(() -> new NotFoundException("Origen no encontrado"));
        var destino = puntoRepo.findById(destinoId)
                .orElseThrow(() -> new NotFoundException("Destino no encontrado"));

        // --- 2) Medio de pago activo (operación SQL interna o PagoService) ---
        if (!pagoService.tieneMedioDePagoActivo(cliente)) {
            throw new BusinessException("El cliente no tiene medio de pago activo");
        }

        // --- 3) Construir servicio base (aún sin conductor/vehículo) ---
        ServicioEntity s;
        String tipo = tipoServicio.trim();
        switch (tipo) {
            case "Transporte" -> {
                var t = new TransporteEntity();
                t.setNivelVehiculo(nivelOpcional != null ? nivelOpcional : NivelVehiculo.ESTANDAR);
                s = t;
            }
            case "Domicilio" -> s = new DomicilioEntity();
            case "Mercancia" -> s = new MercanciaEntity();
            default -> throw new BusinessException("Tipo de servicio inválido: " + tipoServicio);
        }
        s.setTipo(tipo);
        s.setCliente(cliente);
        s.setOrigen(origen);
        s.setDestino(destino);
        s.setCiudad(origen.getCiudad());
        s.setFecha(fecha);
        s.setHoraInicio(null);
        s.setDuracion(0);
        s.setEstado(false);
        s.setTarifaKm(Math.max(1, tarifaKm));
        s.setLongitudServicio(Math.max(1, longitudKm));

        // --- 4) Elegir una disponibilidad y BLOQUEARLA por SQL ---
        DayOfWeek dia = fecha.getDayOfWeek();
        List<DisponibilidadEntity> candidatas = dispRepo.findActivasPorTipoServicioYDia(tipo, dia);

        // Filtrado por ciudad y hora
        candidatas.removeIf(d -> d.getVehiculo() == null
                || d.getVehiculo().getCiudad() == null
                || !Objects.equals(d.getVehiculo().getCiudad().getId(), origen.getCiudad().getId())
                || d.getHorario() == null
                || !horaDentro(d.getHorario(), horaDeseada));

        // Ordenar por cercanía (si hay coords) o por id para determinismo
        double[] cOrigen = coords(origen.getCoordenadas());
        candidatas.sort(Comparator.comparingDouble(d -> {
            double[] cVeh = coordsVeh(d.getVehiculo());
            if (cOrigen == null || cVeh == null) return (double) Long.MAX_VALUE - safeId(d.getConductor());
            return haversineKm(cOrigen, cVeh);
        }));

        DisponibilidadEntity asignada = null;
        for (var d : candidatas) {
            Long conductorId = safeIdLong(d.getConductor());
            if (conductorId != null && servicioRepo.existsByConductorIdAndEstadoTrue(conductorId)) {
                continue; // conductor ya está en servicio
            }
            // Intento de bloqueo atómico (SQL UPDATE con condición)
            int ok = dispRepo.bloquearSiLibre(d.getId());
            if (ok == 1) { // la tomé yo
                asignada = d;
                break;
            }
            // si ok==0, otra transacción la tomó; pruebo siguiente
        }

        // Si no existe cupo, dejamos el servicio en espera (sin cobrar)
        if (asignada == null) {
            s.setCosto(calcularCostoConNivel(s, s.getLongitudServicio(), s.getTarifaKm(), nivelOpcional));
            return servicioRepo.save(s);
        }

        // --- 5) Cobro y registro del inicio (si falla, rollback) ---
        try {
            int costo = calcularCostoConNivel(s, s.getLongitudServicio(), s.getTarifaKm(), nivelOpcional);
            pagoService.cobrar(cliente, costo); // puede lanzar excepción -> rollback

            s.setConductor(asignada.getConductor());
            s.setVehiculo(asignada.getVehiculo());
            s.setCosto(costo);
            s.setHoraInicio(LocalTime.now());
            s.setEstado(true);

            return servicioRepo.save(s);
        } catch (Exception e) {
            // liberar explícitamente (no es estrictamente necesario por rollback, pero es claro)
            dispRepo.liberar(asignada.getId());
            throw e;
        }
    }

    // ---- Helpers ----
    private boolean horaDentro(String rango, LocalTime t) {
        var parts = rango.trim().split("-");
        var ini = LocalTime.parse(parts[0].trim());
        var fin = LocalTime.parse(parts[1].trim());
        return !t.isBefore(ini) && t.isBefore(fin); // [ini, fin)
    }
    private double[] coords(String s) {
        try { return s == null ? null : Geo.parseLatLon(s); } catch (Exception e) { return null; }
    }
    private double[] coordsVeh(VehiculoEntity v) {
        try {
            var m = v.getClass().getMethod("getCoordenadas");
            Object r = m.invoke(v);
            if (r instanceof String) return coords((String) r);
        } catch (Exception ignore) {}
        return null;
    }
    private long safeId(Object o) {
        try { var m = o.getClass().getMethod("getId"); Object r = m.invoke(o);
              return (r instanceof Number) ? ((Number) r).longValue() : Long.MAX_VALUE-1; } catch (Exception e) { return Long.MAX_VALUE-1; }
    }
    private Long safeIdLong(Object o) {
        try { var m = o.getClass().getMethod("getId"); Object r = m.invoke(o);
              return (r instanceof Number) ? ((Number) r).longValue() : null; } catch (Exception e) { return null; }
    }
    private double haversineKm(double[] a, double[] b) {
        double R=6371.0088, lat1=Math.toRadians(a[0]), lon1=Math.toRadians(a[1]),
               lat2=Math.toRadians(b[0]), lon2=Math.toRadians(b[1]);
        double dlat=lat2-lat1, dlon=lon2-lon1;
        double h=Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(lat1)*Math.cos(lat2)*Math.sin(dlon/2)*Math.sin(dlon/2);
        return 2*R*Math.asin(Math.sqrt(h));
    }
    private int calcularCostoConNivel(ServicioEntity s, int km, int tarifaKm, NivelVehiculo nivel) {
        double base = (double)Math.max(1,km) * (double)Math.max(1,tarifaKm);
        if (!"Transporte".equalsIgnoreCase(s.getTipo())) return (int)Math.max(1, Math.round(base));
        NivelVehiculo n = (s instanceof TransporteEntity te && te.getNivelVehiculo()!=null) ? te.getNivelVehiculo() : nivel;
        double factor = switch (n != null ? n : NivelVehiculo.ESTANDAR) {
            case ESTANDAR -> 1.0;
            case CONFORT  -> 1.3;
            case VAN      -> 1.6;
        };
        return (int)Math.max(1, Math.round(base*factor));
    }
}
