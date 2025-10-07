package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioServicioRepository extends JpaRepository<UsuarioServicioEntity, Long> {
    Optional<UsuarioServicioEntity> findByCorreo(String correo);
    Optional<UsuarioServicioEntity> findByCedula(String cedula);
    List<UsuarioServicioEntity> findByNombreContainingIgnoreCase(String nombre);
}

