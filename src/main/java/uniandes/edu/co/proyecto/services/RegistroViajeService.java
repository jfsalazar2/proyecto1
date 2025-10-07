package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
public class RegistroViajeService {
    private final ServicioRepository servicioRepo;
    private final UsuarioConductorRepository conductorRepo;

    public RegistroViajeService(ServicioRepository servicioRepo, UsuarioConductorRepository conductorRepo) {
        this.servicioRepo = servicioRepo; this.conductorRepo = conductorRepo;
    }

    @Transactional
    public ServicioEntity rf9RegistrarViaje(Long servicioId, LocalTime horaInicio, LocalTime horaFin,
                                            int costoFinal, int duracionMin) {
        ServicioEntity s = servicioRepo.findById(servicioId)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        s.setHoraInicio(horaInicio);
        s.setHoraFin(horaFin);
        s.setCosto(costoFinal);
        s.setDuracion(duracionMin);
        s.setEstado(true);

        if (s.getConductor() != null) {
            UsuarioConductorEntity c = s.getConductor();
            c.setDisponible(true);
        }
        return servicioRepo.save(s);
    }
}
