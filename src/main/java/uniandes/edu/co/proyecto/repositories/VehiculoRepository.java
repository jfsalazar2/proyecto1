package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.enums.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {
    Optional<VehiculoEntity> findByPlaca(String placa);
    List<VehiculoEntity> findByTipo(TipoVehiculo tipo);
    List<VehiculoEntity> findByCiudad(CiudadEntity ciudad);
    List<VehiculoEntity> findByMarcaContainingIgnoreCase(String marca);
    List<VehiculoEntity> findByModeloContainingIgnoreCase(String modelo);
}
