package uniandes.edu.co.proyecto.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.ResenaEntity;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.ResenaRepository;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;

import java.lang.reflect.Method;
import java.util.Objects;

@Service
public class ResenaService {

    private final ResenaRepository repo;
    private final ServicioRepository servicioRepo;
    private final UsuarioConductorRepository conductorRepo;
    private final UsuarioServicioRepository clienteRepo;

    public ResenaService(ResenaRepository repo,
                         ServicioRepository servicioRepo,
                         UsuarioConductorRepository conductorRepo,
                         UsuarioServicioRepository clienteRepo) {
        this.repo = repo;
        this.servicioRepo = servicioRepo;
        this.conductorRepo = conductorRepo;
        this.clienteRepo = clienteRepo;
    }

    // ================== RF10: Cliente reseña a Conductor ==================
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public ResenaEntity rf10ClienteAConductor(Long servicioId,
                                              Long autorClienteId,
                                              Long conductorResenadoId,
                                              int calificacion,
                                              String comentario) {
        
        if (calificacion < 1 || calificacion > 5)
            throw new BusinessException("La calificación debe estar entre 1 y 5");

        ServicioEntity s = servicioRepo.findById(servicioId)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        UsuarioServicioEntity autor = clienteRepo.findById(autorClienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        UsuarioConductorEntity conductor = conductorRepo.findById(conductorResenadoId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));

        
        if (s.getCliente() == null || !Objects.equals(s.getCliente().getId(), autor.getId())) {
            throw new BusinessException("El autor no corresponde al cliente del servicio");
        }
        
        if (s.getConductor() == null || !Objects.equals(s.getConductor().getId(), conductor.getId())) {
            throw new BusinessException("El conductor reseñado no corresponde al servicio");
        }

        
        if (!estaFinalizado(s)) {
            throw new BusinessException("Solo se puede reseñar servicios finalizados");
        }

        
        if (repo.existsByServicioIdAndAutorClienteIdAndConductorResenadoId(servicioId, autorClienteId, conductorResenadoId)) {
            throw new BusinessException("Ya existe una reseña de este cliente hacia este conductor para este servicio");
        }

        ResenaEntity r = new ResenaEntity();
        r.setServicio(s);
        
        r.setAutor(autor);
        
        r.setConductorResenado(conductor);
        r.setCalificacion(calificacion);
        r.setComentario(comentario);

        ResenaEntity guardada = repo.save(r);

        
        actualizarPromedio(conductor, calificacion);
        conductorRepo.save(conductor);

        return guardada;
    }

    // ================== RF11: Conductor reseña a Cliente ==================
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public ResenaEntity rf11ConductorACliente(Long servicioId,
                                              Long autorConductorId,
                                              Long clienteResenadoId,
                                              int calificacion,
                                              String comentario) {
        if (calificacion < 1 || calificacion > 5)
            throw new BusinessException("La calificación debe estar entre 1 y 5");

        ServicioEntity s = servicioRepo.findById(servicioId)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));
        UsuarioConductorEntity autor = conductorRepo.findById(autorConductorId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));
        UsuarioServicioEntity clienteRes = clienteRepo.findById(clienteResenadoId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        
        if (s.getConductor() == null || !Objects.equals(s.getConductor().getId(), autor.getId())) {
            throw new BusinessException("El autor no corresponde al conductor del servicio");
        }
        
        if (s.getCliente() == null || !Objects.equals(s.getCliente().getId(), clienteRes.getId())) {
            throw new BusinessException("El cliente reseñado no corresponde al servicio");
        }

        if (!estaFinalizado(s)) {
            throw new BusinessException("Solo se puede reseñar servicios finalizados");
        }

        
        if (repo.existsByServicioIdAndAutorConductorIdAndClienteResenadoId(servicioId, autorConductorId, clienteResenadoId)) {
            throw new BusinessException("Ya existe una reseña de este conductor hacia este cliente para este servicio");
        }

        ResenaEntity r = new ResenaEntity();
        r.setServicio(s);

        
        setIfExists(r, "setAutorConductor", UsuarioConductorEntity.class, autor);

        
        setIfExists(r, "setClienteResenado", UsuarioServicioEntity.class, clienteRes);

        
        r.setCalificacion(calificacion);
        r.setComentario(comentario);

        ResenaEntity guardada = repo.save(r);

        
        actualizarPromedio(clienteRes, calificacion);
        clienteRepo.save(clienteRes);

        return guardada;
    }

    

    private boolean estaFinalizado(ServicioEntity s) {
    try {
        
        return s.getEstado() && s.getHoraFin() != null;
    } catch (Exception e) {
        
        return true;
    }
 }



   
    private void actualizarPromedio(Object destinatario, int nuevaCalificacion) {
        if (destinatario == null) return;
        try {
            
            Method getProm = destinatario.getClass().getMethod("getCalificacionPromedio");
            Method getTotal = destinatario.getClass().getMethod("getTotalResenas");
            Method setProm = destinatario.getClass().getMethod("setCalificacionPromedio", Double.class);
            Method setTotal = destinatario.getClass().getMethod("setTotalResenas", Integer.class);

            Double promedio = (Double) getProm.invoke(destinatario);
            Integer total = (Integer) getTotal.invoke(destinatario);
            if (promedio == null) promedio = 0d;
            if (total == null) total = 0;

            double suma = promedio * total + nuevaCalificacion;
            int nuevoTotal = total + 1;
            double nuevoProm = suma / nuevoTotal;

            setProm.invoke(destinatario, nuevoProm);
            setTotal.invoke(destinatario, nuevoTotal);
        } catch (Exception ignore) {
            
        }
    }

    
    private <T> void setIfExists(Object target, String setter, Class<T> paramType, T value) {
        try {
            Method m = target.getClass().getMethod(setter, paramType);
            m.invoke(target, value);
        } catch (Exception ignore) { /* no-op */ }
    }
}
