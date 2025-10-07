package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.MercanciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MercanciaRepository extends JpaRepository<MercanciaEntity, Long> {
    List<MercanciaEntity> findByDescripcionContainingIgnoreCase(String descripcion);
}

