package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.NivelVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelVehiculoRepository extends JpaRepository<NivelVehiculo, String> {
}
