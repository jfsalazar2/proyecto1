package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {
}
