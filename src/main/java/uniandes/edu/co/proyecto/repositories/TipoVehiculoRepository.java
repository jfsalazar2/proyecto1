package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, String> {
}

