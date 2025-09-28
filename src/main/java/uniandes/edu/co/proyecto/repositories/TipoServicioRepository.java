package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoServicioRepository extends JpaRepository<TipoServicio, String> {
}
