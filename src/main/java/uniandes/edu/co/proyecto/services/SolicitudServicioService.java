package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.entities.enums.NivelVehiculo;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.*;
import uniandes.edu.co.proyecto.util.Geo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
public class SolicitudServicioService {
    private final ServicioRepository servicioRepo;
    private final UsuarioServicioRepository clienteRepo;
    private final PuntoGeograficoRepository puntoRepo;
    private final UsuarioConductorRepository conductorRepo;
    private final VehiculoRepository vehiculoRepo;
    private final DisponibilidadRepository disponibilidadRepo;

    public SolicitudServicioService(ServicioRepository servicioRepo,
                                    UsuarioServicioRepository clienteRepo,
                                    PuntoGeograficoRepository puntoRepo,
                                    UsuarioConductorRepository conductorRepo,
                                    VehiculoRepository vehiculoRepo,
                                    DisponibilidadRepository disponibilidadRepo) {
        this.servicioRepo = servicioRepo;
        this.clienteRepo = clienteRepo;
        this.puntoRepo = puntoRepo;
        this.conductorRepo = conductorRepo;
        this.vehiculoRepo = vehiculoRepo;
        this.disponibilidadRepo = disponibilidadRepo;
    }

    @Transactional
    public ServicioEntity rf8SolicitarServicio(Long clienteId, String tipoServicio,
                                               Long ciudadId, Long origenId, Long destinoId,
                                               int tarifaKm, int longitudKm,
                                               LocalDate fecha, LocalTime horaDeseada,
                                               NivelVehiculo nivelViajeOpcional) {
        UsuarioServicioEntity cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        PuntoGeograficoEntity origen = puntoRepo.findById(origenId)
                .orElseThrow(() -> new NotFoundException("Origen no encontrado"));
        PuntoGeograficoEntity destino = puntoRepo.findById(destinoId)
                .orElseThrow(() -> new NotFoundException("Destino no encontrado"));

        // 1) costo base
        int costo = Math.max(1, longitudKm) * Math.max(1, tarifaKm);

        // 2) crear Servicio base
        ServicioEntity s;
        switch (tipoServicio) {
            case "Transporte" -> {
                TransporteEntity t = new TransporteEntity();
                t.setNivelVehiculo(nivelViajeOpcional == null ? NivelVehiculo.ESTANDAR : nivelViajeOpcional);
                s = t;
            }
            case "Domicilio" -> s = new DomicilioEntity();
            case "Mercancia" -> s = new MercanciaEntity();
            default -> throw new BusinessException("Tipo de servicio inválido: " + tipoServicio);
        }
        s.setTipo(tipoServicio);
        s.setTarifaKm(tarifaKm);
        s.setLongitudServicio(longitudKm);
        s.setEstado(false);
        s.setCosto(costo);
        s.setDuracion(0);
        s.setHoraInicio(horaDeseada);
        s.setFecha(fecha);
        s.setCliente(cliente);
        s.setCiudad(origen.getCiudad()); // ciudad por el origen
        s.setOrigen(origen);
        s.setDestino(destino);

        // 3) elegir candidato disponible y "cercano"
        // criterio simple: misma ciudad + disponibilidad por tipo + distancia mínima al origen por coordenadas
        List<DisponibilidadEntity> candidatos = disponibilidadRepo.findByTipoServicio(tipoServicio);
        double[] cOrigen = Geo.parseLatLon(origen.getCoordenadas());

        var mejor = candidatos.stream()
                .filter(d -> d.getVehiculo() != null && d.getVehiculo().getCiudad() != null)
                .filter(d -> d.getVehiculo().getCiudad().getId().equals(origen.getCiudad().getId()))
                .min(Comparator.comparingDouble(d -> {
                    if (cOrigen == null) return Double.MAX_VALUE;
                    var v = d.getVehiculo();
                    // Si tuvieras posición del vehículo, úsala; aquí no la tenemos -> heurística: 0
                    return 0.0;
                }));

        if (mejor.isPresent()) {
            s.setConductor(mejor.get().getConductor());
            s.setVehiculo(mejor.get().getVehiculo());
        } else {
            // sin asignación automática, queda en espera
        }

        return servicioRepo.save(s);
    }
}
