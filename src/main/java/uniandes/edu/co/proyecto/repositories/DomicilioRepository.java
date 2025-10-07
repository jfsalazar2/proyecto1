package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.DomicilioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRepository extends JpaRepository<DomicilioEntity, Long> {
}

