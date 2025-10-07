package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CiudadRepository extends JpaRepository<CiudadEntity, Long> {
    Optional<CiudadEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
