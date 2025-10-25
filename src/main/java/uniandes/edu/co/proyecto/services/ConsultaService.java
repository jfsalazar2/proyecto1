package uniandes.edu.co.proyecto.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.dtos.*;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaService {

    private final ServicioRepository servicioRepo;

    public ConsultaService(ServicioRepository servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    // RFC1
    @Transactional(readOnly = true)
    public List<ServicioResumenDTO> rfc1HistoricoCliente(Long clienteId) {
        List<ServicioEntity> servicios = servicioRepo.findByClienteIdOrderByFechaDescHoraInicioDesc(clienteId);
        List<ServicioResumenDTO> out = new ArrayList<>(servicios.size());
        for (ServicioEntity s : servicios) {
            out.add(new ServicioResumenDTO(
                    s.getId(),
                    s.getTipo(),
                    s.getCosto(),
                    s.getDuracion(),
                    s.getFecha(),
                    s.getHoraInicio(),
                    s.getHoraFin(),
                    s.getConductor() != null ? s.getConductor().getId() : null,
                    s.getConductor() != null ? s.getConductor().getNombre() : null,
                    s.getVehiculo() != null ? s.getVehiculo().getId() : null,
                    s.getVehiculo() != null ? s.getVehiculo().getPlaca() : null,
                    s.getOrigen() != null ? s.getOrigen().getId() : null,
                    s.getOrigen() != null ? s.getOrigen().getNombre() : null,
                    s.getDestino() != null ? s.getDestino().getId() : null,
                    s.getDestino() != null ? s.getDestino().getNombre() : null
            ));
        }
        return out;
    }

    // RFC2
    @Transactional(readOnly = true)
    public List<TopConductorDTO> rfc2Top20Conductores() {
        var rows = servicioRepo.topConductores(PageRequest.of(0, 20));
        List<TopConductorDTO> out = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            Long id = (Long) r[0];
            String nombre = (String) r[1];
            Long total = (Long) r[2];
            out.add(new TopConductorDTO(id, nombre, total));
        }
        return out;
    }

    // RFC3
    @Transactional(readOnly = true)
    public List<TotalesVehiculoDTO> rfc3TotalesPorVehiculo(Long conductorId, double porcentajeComision) {
        double factor = 1.0 - Math.max(0.0, Math.min(1.0, porcentajeComision));
        var rows = servicioRepo.totalesPorVehiculoYTipo(conductorId);
        List<TotalesVehiculoDTO> out = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            Long vehiculoId = (Long) r[0];
            String placa = (String) r[1];
            String tipo = (String) r[2];
            Long cantidad = (Long) r[3];
            Long bruto = ((Number) r[4]).longValue();
            Long neto = Math.round(bruto * factor);
            out.add(new TotalesVehiculoDTO(vehiculoId, placa, tipo, cantidad, bruto, neto));
        }
        return out;
    }

    // RFC4
    @Transactional(readOnly = true)
    public List<UsoServicioDTO> rfc4UsoPorCiudadYRango(Long ciudadId, LocalDate ini, LocalDate fin) {
        var rows = servicioRepo.usoServiciosPorCiudadYRango(ciudadId, ini, fin);
        long total = 0L;
        for (Object[] r : rows) total += ((Number) r[2]).longValue();

        List<UsoServicioDTO> out = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            String tipo = (String) r[0];
            
            var nivel = (r[1] == null) ? null : (uniandes.edu.co.proyecto.entities.enums.NivelVehiculo) r[1];
            Long cnt = ((Number) r[2]).longValue();
            double pct = (total > 0) ? (cnt * 100.0 / total) : 0.0;
            out.add(new UsoServicioDTO(tipo, nivel, cnt, pct));
        }
        return out;
    }
}

