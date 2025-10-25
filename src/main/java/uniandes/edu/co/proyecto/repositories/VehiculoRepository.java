package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {
    boolean existsByPlacaIgnoreCase(String placa);
    boolean existsByPropietarioIdAndActivoTrue(Long propietarioId); // si manejas flag `activo`
}

