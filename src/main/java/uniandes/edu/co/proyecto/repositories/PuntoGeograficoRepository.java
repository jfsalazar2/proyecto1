package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntoGeograficoRepository extends JpaRepository<PuntoGeograficoEntity, Long> {
    List<PuntoGeograficoEntity> findByCiudadAndNombreContainingIgnoreCase(CiudadEntity ciudad, String nombre);
    List<PuntoGeograficoEntity> findByNombreContainingIgnoreCase(String nombre);
}
