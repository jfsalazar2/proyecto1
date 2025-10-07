package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.util.HorarioParser;
import uniandes.edu.co.proyecto.util.TimeRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DisponibilidadService {
    private final DisponibilidadRepository repo;
    private final UsuarioConductorRepository conductorRepo;
    private final VehiculoRepository vehiculoRepo;

    public DisponibilidadService(DisponibilidadRepository repo, UsuarioConductorRepository conductorRepo, VehiculoRepository vehiculoRepo) {
        this.repo = repo; this.conductorRepo = conductorRepo; this.vehiculoRepo = vehiculoRepo;
    }

    @Transactional
    public DisponibilidadEntity rf5RegistrarDisponibilidad(Long conductorId, Long vehiculoId,
                                                           String tipoServicio, List<String> dias, String horario) {
        UsuarioConductorEntity conductor = conductorRepo.findById(conductorId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));
        VehiculoEntity vehiculo = vehiculoRepo.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("Vehículo no encontrado"));

        if (!HorarioParser.isValid(horario))
            throw new BusinessException("Horario inválido. Formato esperado HH:mm-HH:mm");

        // Validar que no se superponga con otras disponibilidades del mismo conductor
        TimeRange nuevo = HorarioParser.parse(horario).orElseThrow();
        List<DisponibilidadEntity> existentes = repo.findByConductor(conductor);
        for (DisponibilidadEntity d : existentes) {
            if (d.getId() == null) continue;
            if (Collections.disjoint(d.getDias(), dias)) continue; // no comparten día -> OK
            TimeRange tr = HorarioParser.parse(d.getHorario())
                    .orElse(null);
            if (tr != null && tr.overlaps(nuevo))
                throw new BusinessException("Disponibilidad se superpone con id=" + d.getId());
        }

        DisponibilidadEntity disp = new DisponibilidadEntity();
        disp.setConductor(conductor);
        disp.setVehiculo(vehiculo);
        disp.setTipoServicio(tipoServicio);
        disp.setDias(new ArrayList<>(dias));
        disp.setHorario(horario);
        return repo.save(disp);
    }

    @Transactional
    public DisponibilidadEntity rf6ModificarDisponibilidad(Long disponibilidadId,
                                                           List<String> nuevosDias, String nuevoHorario) {
        DisponibilidadEntity d = repo.findById(disponibilidadId)
                .orElseThrow(() -> new NotFoundException("Disponibilidad no encontrada"));
        if (!HorarioParser.isValid(nuevoHorario))
            throw new BusinessException("Horario inválido");

        TimeRange nuevo = HorarioParser.parse(nuevoHorario).orElseThrow();
        List<DisponibilidadEntity> existentes = repo.findByConductor(d.getConductor());
        for (DisponibilidadEntity e : existentes) {
            if (Objects.equals(e.getId(), d.getId())) continue;
            if (Collections.disjoint(e.getDias(), nuevosDias)) continue;
            TimeRange tr = HorarioParser.parse(e.getHorario()).orElse(null);
            if (tr != null && tr.overlaps(nuevo))
                throw new BusinessException("Se superpone con disponibilidad id=" + e.getId());
        }
        d.setDias(new ArrayList<>(nuevosDias));
        d.setHorario(nuevoHorario);
        return repo.save(d);
    }
}
