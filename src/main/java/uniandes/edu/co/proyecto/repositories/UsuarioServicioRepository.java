package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;

public interface UsuarioServicioRepository extends JpaRepository<UsuarioServicioEntity, Long> {
    boolean existsByCorreoIgnoreCase(String correo);
    boolean existsByCedula(String cedula);
}


