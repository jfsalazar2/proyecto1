package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.ResenaEntity;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.ResenaRepository;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResenaService {
    private final ResenaRepository repo;
    private final ServicioRepository servicioRepo;
    private final UsuarioConductorRepository conductorRepo;
    private final UsuarioServicioRepository clienteRepo;

    public ResenaService(ResenaRepository repo, ServicioRepository servicioRepo,
                         UsuarioConductorRepository conductorRepo, UsuarioServicioRepository clienteRepo) {
        this.repo = repo; this.servicioRepo = servicioRepo;
        this.conductorRepo = conductorRepo; this.clienteRepo = clienteRepo;
    }

    // RF10
    @Transactional
    public ResenaEntity rf10ClienteAConductor(Long servicioId, Long autorClienteId, Long conductorResenadoId,
                                              int calificacion, String comentario) {
        ServicioEntity s = servicioRepo.findById(servicioId)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        UsuarioServicioEntity autor = clienteRepo.findById(autorClienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        UsuarioConductorEntity conductor = conductorRepo.findById(conductorResenadoId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));

        ResenaEntity r = new ResenaEntity();
        r.setServicio(s);
        r.setAutor(autor);
        r.setConductorResenado(conductor);
        r.setCalificacion(calificacion);
        r.setComentario(comentario);
        return repo.save(r);
    }

    // RF11 
    @Transactional
    public ResenaEntity rf11ConductorACliente(Long servicioId, Long autorConductorId, Long clienteId,
                                              int calificacion, String comentario) {
        ServicioEntity s = servicioRepo.findById(servicioId)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        UsuarioConductorEntity autor = conductorRepo.findById(autorConductorId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));
        UsuarioServicioEntity cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        ResenaEntity r = new ResenaEntity();
        r.setServicio(s);
        r.setAutor(null); // <- si tu modelo necesita distinguir, podrías reutilizar 'autor' o ampliar el modelo
        r.setCalificacion(calificacion);
        r.setComentario(comentario);
        // Nota: Si agregas `clienteResenado` en la entidad, setéalo aquí.
        return repo.save(r);
    }
}