package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioConductorRepository extends JpaRepository<UsuarioConductorEntity, Long> {
    Optional<UsuarioConductorEntity> findByCorreo(String correo);
    Optional<UsuarioConductorEntity> findByCedula(String cedula);
    List<UsuarioConductorEntity> findByDisponibleTrue();
    List<UsuarioConductorEntity> findByCiudad(CiudadEntity ciudad);
    List<UsuarioConductorEntity> findByNombreContainingIgnoreCase(String nombre);
}

