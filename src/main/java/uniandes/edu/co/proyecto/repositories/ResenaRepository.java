package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.ResenaEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<ResenaEntity, Long> {
    List<ResenaEntity> findByConductorResenado(UsuarioConductorEntity conductor);
    List<ResenaEntity> findByServicio(ServicioEntity servicio);
    List<ResenaEntity> findByCalificacionBetween(Integer min, Integer max);
}

