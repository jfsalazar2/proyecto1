package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, String> {
}
