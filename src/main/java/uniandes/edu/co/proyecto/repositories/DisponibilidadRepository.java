package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
}
