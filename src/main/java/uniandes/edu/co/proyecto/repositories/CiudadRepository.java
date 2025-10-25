package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniandes.edu.co.proyecto.entities.CiudadEntity;

public interface CiudadRepository extends JpaRepository<CiudadEntity, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}

