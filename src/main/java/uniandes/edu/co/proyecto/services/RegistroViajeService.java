package uniandes.edu.co.proyecto.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

@Service
public class RegistroViajeService {

    private final ServicioRepository servicioRepo;
    private final UsuarioConductorRepository conductorRepo;

    public RegistroViajeService(ServicioRepository servicioRepo,
                                UsuarioConductorRepository conductorRepo) {
        this.servicioRepo = servicioRepo;
        this.conductorRepo = conductorRepo;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public ServicioEntity rf9RegistrarViaje(Long servicioId,
                                            LocalTime horaInicio,
                                            LocalTime horaFin,
                                            int costoFinal,
                                            Integer duracionMin) {
        ServicioEntity s = servicioRepo.findById(servicioId)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));

        
        if (horaInicio != null && horaFin != null && !horaInicio.isBefore(horaFin)) {
            throw new BusinessException("La hora de inicio debe ser menor que la hora de fin");
        }

        
        if (horaInicio != null) s.setHoraInicio(horaInicio);
        if (horaFin != null)    s.setHoraFin(horaFin);

        
        Integer duracion = duracionMin;
        if (s.getHoraInicio() != null && s.getHoraFin() != null) {
            long mins = Duration.between(s.getHoraInicio(), s.getHoraFin()).toMinutes();
            if (mins < 0) throw new BusinessException("La duración calculada es negativa (verifique horas)");
            if (duracion == null || !Objects.equals(duracion, (int) mins)) {
                duracion = (int) mins;
            }
        }
        if (duracion == null) {
            
            throw new BusinessException("Debe registrar duración o ambas horas para calcularla");
        }
        s.setDuracion(duracion);

        
        if (costoFinal <= 0) {
            throw new BusinessException("El costo final debe ser mayor que 0");
        }
        s.setCosto(costoFinal);

        
        s.setEstado(true);

        
        if (s.getConductor() != null) {
            UsuarioConductorEntity c = s.getConductor();
            try {
                
                c.setDisponible(true);
            } catch (Exception ignore) { /* noop */ }
            conductorRepo.save(c);
        }

        

        return servicioRepo.save(s);
    }

}

