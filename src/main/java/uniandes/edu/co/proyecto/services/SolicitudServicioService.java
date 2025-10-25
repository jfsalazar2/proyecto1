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
public class SolicitudServicioService {

    private final ServicioRepository servicioRepo;
    private final UsuarioServicioRepository clienteRepo;
    private final PuntoGeograficoRepository puntoRepo;
    private final DisponibilidadRepository disponibilidadRepo;
    private final PagoService pagoService; 

    public SolicitudServicioService(ServicioRepository servicioRepo,
                                    UsuarioServicioRepository clienteRepo,
                                    PuntoGeograficoRepository puntoRepo,
                                    DisponibilidadRepository disponibilidadRepo,
                                    PagoService pagoService) {
        this.servicioRepo = servicioRepo;
        this.clienteRepo = clienteRepo;
        this.puntoRepo = puntoRepo;
        this.disponibilidadRepo = disponibilidadRepo;
        this.pagoService = pagoService;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public ServicioEntity rf8SolicitarServicio(Long clienteId,
                                               String tipoServicio,
                                               Long ciudadId,               
                                               Long origenId,
                                               Long destinoId,
                                               int tarifaKm,
                                               int longitudKm,
                                               LocalDate fecha,
                                               LocalTime horaDeseada,
                                               NivelVehiculo nivelViajeOpcional) {

        
        if (clienteId == null) throw new BusinessException("El cliente es obligatorio");
        if (origenId == null)  throw new BusinessException("El punto de origen es obligatorio");
        if (destinoId == null) throw new BusinessException("El punto de destino es obligatorio");
        if (tipoServicio == null || tipoServicio.isBlank()) throw new BusinessException("El tipo de servicio es obligatorio");
        if (fecha == null) throw new BusinessException("La fecha es obligatoria");
        if (horaDeseada == null) throw new BusinessException("La hora deseada es obligatoria");

        var cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        var origen = puntoRepo.findById(origenId)
                .orElseThrow(() -> new NotFoundException("Origen no encontrado"));
        var destino = puntoRepo.findById(destinoId)
                .orElseThrow(() -> new NotFoundException("Destino no encontrado"));

        if (ciudadId != null) {
            if (origen.getCiudad() == null || !Objects.equals(origen.getCiudad().getId(), ciudadId))
                throw new BusinessException("El origen no pertenece a la ciudad indicada");
            if (destino.getCiudad() == null || !Objects.equals(destino.getCiudad().getId(), ciudadId))
                throw new BusinessException("El destino no pertenece a la ciudad indicada");
        }

        
        if (!pagoService.tieneMedioDePagoActivo(cliente)) {
            throw new BusinessException("El cliente no tiene un medio de pago activo");
        }

        
        String tipo = tipoServicio.trim();
        ServicioEntity s;
        switch (tipo) {
            case "Transporte" -> {
                var t = new TransporteEntity();
                t.setNivelVehiculo(nivelViajeOpcional != null ? nivelViajeOpcional : NivelVehiculo.ESTANDAR);
                s = t;
            }
            case "Domicilio"  -> s = new DomicilioEntity();
            case "Mercancia"  -> s = new MercanciaEntity();
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

        
        var dia = fecha.getDayOfWeek();
        var diaEs = mapDiaAEs(dia);
        var candidatas = disponibilidadRepo.findActivasPorTipoServicioYDia(tipo, dia);

        
        var hora = horaDeseada;
        List<DisponibilidadEntity> elegibles = new ArrayList<>();
        for (var d : candidatas) {
            var veh = d.getVehiculo();
            if (veh == null || veh.getCiudad() == null) continue;
            if (origen.getCiudad() == null || !Objects.equals(veh.getCiudad().getId(), origen.getCiudad().getId()))
                continue;

            if (d.getDias() == null || d.getHorario() == null || d.getHorario().isBlank()) continue;
            boolean diaOk = d.getDias().stream()
                    .filter(Objects::nonNull).map(String::trim).map(String::toUpperCase)
                    .map(this::sinTilde).anyMatch(v -> v.equals(diaEs));
            if (!diaOk) continue;

            if (!horaDentro(d.getHorario(), hora)) continue;

            if (isTrue(d, "isEnUso")) continue; 

            if (nivelViajeOpcional != null && !vehiculoCumpleNivel(veh, nivelViajeOpcional)) continue;

            
            if (servicioRepo.existsByConductorIdAndEstadoTrue(safeIdLong(d.getConductor()))) continue;

            elegibles.add(d);
        }

        
        if (elegibles.isEmpty()) {
            s.setCosto(calcularCostoConNivel(s, s.getLongitudServicio(), s.getTarifaKm(), nivelViajeOpcional));
            return servicioRepo.save(s);
        }

        
        double[] cOrigen = coords(origen.getCoordenadas());
        var mejorOpt = elegibles.stream().min(Comparator.comparingDouble(d -> {
            double[] cVeh = coordsVeh(d.getVehiculo());
            if (cOrigen == null || cVeh == null) return Double.MAX_VALUE - safeId(d.getConductor());
            return haversineKm(cOrigen, cVeh);
        }));

        if (mejorOpt.isEmpty()) {
            s.setCosto(calcularCostoConNivel(s, s.getLongitudServicio(), s.getTarifaKm(), nivelViajeOpcional));
            return servicioRepo.save(s);
        }

        var elegido = mejorOpt.get();

        
        marcarEnUso(elegido, true);                 
        disponibilidadRepo.save(elegido);

        try {
            
            int costo = calcularCostoConNivel(s, s.getLongitudServicio(), s.getTarifaKm(), nivelViajeOpcional);

            
            pagoService.cobrar(cliente, costo);

            
            s.setConductor(elegido.getConductor());
            s.setVehiculo(elegido.getVehiculo());
            s.setCosto(costo);
            s.setHoraInicio(LocalTime.now());
            s.setEstado(true); // iniciado

            return servicioRepo.save(s);
        } catch (Exception ex) {
            
            marcarEnUso(elegido, false);
            disponibilidadRepo.save(elegido);
            throw ex;
        }
    }

    

    private int calcularCostoConNivel(ServicioEntity s, int km, int tarifaKm, NivelVehiculo nivel) {
    double base = (double) Math.max(1, km) * (double) Math.max(1, tarifaKm);
    double factor = 1.0;

    if ("Transporte".equalsIgnoreCase(s.getTipo())) {
        NivelVehiculo n = nivel;
        if (n == null && s instanceof TransporteEntity te) {
            n = te.getNivelVehiculo(); 
        }
        if (n != null) {
            switch (n) {
                case ESTANDAR: factor = 1.0; break;
                case CONFORT:  factor = 1.3; break;  
                case VAN:      factor = 1.6; break;  
                default:       factor = 1.0;
            }
        }
    }
    return (int) Math.max(1, Math.round(base * factor));
}


    private boolean horaDentro(String rango, LocalTime t) {
        var parts = rango.trim().split("-");
        if (parts.length != 2) throw new BusinessException("Horario inválido (HH:mm-HH:mm)");
        var ini = LocalTime.parse(parts[0].trim());
        var fin = LocalTime.parse(parts[1].trim());
        return !t.isBefore(ini) && t.isBefore(fin); // [ini, fin)
    }

    private String sinTilde(String s) {
        return s.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U");
    }

    private String mapDiaAEs(DayOfWeek d) {
        switch (d) {
            case MONDAY:    return "LUNES";
            case TUESDAY:   return "MARTES";
            case WEDNESDAY: return "MIERCOLES";
            case THURSDAY:  return "JUEVES";
            case FRIDAY:    return "VIERNES";
            case SATURDAY:  return "SABADO";
            case SUNDAY:    return "DOMINGO";
            default:        return d.name();
        }
    }

    private boolean vehiculoCumpleNivel(VehiculoEntity v, NivelVehiculo req) {
    if (v == null || req == null) return false;
    try {
        
        java.lang.reflect.Method m = v.getClass().getMethod("getNivel");
        Object r = m.invoke(v);
        if (r instanceof NivelVehiculo nv) {
            return nv == req;
        }
    } catch (Exception ignore) {
        
        return true;
    }
    return false;
}

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void marcarEnUso(Object disp, boolean valor) {
        try {
            var m = disp.getClass().getMethod("setEnUso", boolean.class);
            m.invoke(disp, valor);
            return;
        } catch (Exception ignore) {}
        try {
            Class<?> enumCls = Class.forName("uniandes.edu.co.proyecto.entities.enums.EstadoDisponibilidad");
            Object nuevo = Enum.valueOf((Class<Enum>) enumCls, valor ? "BLOQUEADA" : "ACTIVA");
            var m = disp.getClass().getMethod("setEstado", enumCls);
            m.invoke(disp, nuevo);
        } catch (Exception ignore) { /* si no existe, no bloqueamos */ }
    }

    private boolean isTrue(Object target, String getter) {
        try {
            var m = target.getClass().getMethod(getter);
            Object r = m.invoke(target);
            return r instanceof Boolean && (Boolean) r;
        } catch (Exception ignore) { return false; }
    }

    private long safeId(Object o) {
        try {
            var m = o.getClass().getMethod("getId");
            Object r = m.invoke(o);
            return (r instanceof Number) ? ((Number) r).longValue() : Long.MAX_VALUE - 1;
        } catch (Exception e) {
            return Long.MAX_VALUE - 1;
        }
    }

    private long safeIdLong(Object o) { return safeId(o); }

    private double[] coords(String coord) {
        try { return coord == null ? null : Geo.parseLatLon(coord); }
        catch (Exception e) { return null; }
    }

    private double[] coordsVeh(VehiculoEntity v) {
        if (v == null) return null;
        try {
            var m = v.getClass().getMethod("getCoordenadas");
            var r = m.invoke(v);
            if (r instanceof String) return coords((String) r);
        } catch (Exception ignore) {}
        try {
            var mu = v.getClass().getMethod("getUbicacion");
            var u = mu.invoke(v);
            if (u != null) {
                var mc = u.getClass().getMethod("getCoordenadas");
                var r = mc.invoke(u);
                if (r instanceof String) return coords((String) r);
            }
        } catch (Exception ignore) {}
        return null;
    }

    private double haversineKm(double[] a, double[] b) {
        double R = 6371.0088;
        double lat1 = Math.toRadians(a[0]), lon1 = Math.toRadians(a[1]);
        double lat2 = Math.toRadians(b[0]), lon2 = Math.toRadians(b[1]);
        double dlat = lat2 - lat1, dlon = lon2 - lon1;
        double h = Math.sin(dlat/2)*Math.sin(dlat/2) +
                   Math.cos(lat1)*Math.cos(lat2)*Math.sin(dlon/2)*Math.sin(dlon/2);
        return 2 * R * Math.asin(Math.sqrt(h));
    }
}

