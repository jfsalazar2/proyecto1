package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.TransporteEntity;
import uniandes.edu.co.proyecto.entities.enums.NivelVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransporteRepository extends JpaRepository<TransporteEntity, Long> {
    List<TransporteEntity> findByNivelViaje(NivelVehiculo nivelvVehiculo);
}
