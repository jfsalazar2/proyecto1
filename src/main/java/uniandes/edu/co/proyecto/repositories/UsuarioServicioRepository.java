package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.UsuarioServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioServicioRepository extends JpaRepository<UsuarioServicio, Long> {
}

