package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;

public interface UsuarioConductorRepository extends JpaRepository<UsuarioConductorEntity, Long> {
    boolean existsByCorreoIgnoreCase(String correo);
    boolean existsByCedula(String cedula);
}


