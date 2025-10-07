package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilidadRepository extends JpaRepository<DisponibilidadEntity, Long> {
    List<DisponibilidadEntity> findByConductor(UsuarioConductorEntity conductor);
    List<DisponibilidadEntity> findByVehiculo(VehiculoEntity vehiculo);
    List<DisponibilidadEntity> findByTipoServicio(String tipoServicio);
}
