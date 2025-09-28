package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.Mercancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercanciaRepository extends JpaRepository<Mercancia, Long> {
}

