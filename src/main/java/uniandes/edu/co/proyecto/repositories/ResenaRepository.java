package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
}

