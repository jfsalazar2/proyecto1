package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.ResenaEntity;

@Repository
public interface ResenaRepository extends JpaRepository<ResenaEntity, Long> {

    
    boolean existsByServicioIdAndAutorClienteIdAndConductorResenadoId(Long servicioId,
                                                                       Long autorClienteId,
                                                                       Long conductorResenadoId);

    
    boolean existsByServicioIdAndAutorConductorIdAndClienteResenadoId(Long servicioId,
                                                                       Long autorConductorId,
                                                                       Long clienteResenadoId);
}


