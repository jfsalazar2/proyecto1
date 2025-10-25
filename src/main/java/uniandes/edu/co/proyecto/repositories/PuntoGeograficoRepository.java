package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;

import java.math.BigDecimal;

@Repository
public interface PuntoGeograficoRepository extends JpaRepository<PuntoGeograficoEntity, Long> {

    boolean existsByCiudadAndNombreIgnoreCase(CiudadEntity ciudad, String nombre);

    boolean existsByCiudadAndDireccionIgnoreCase(CiudadEntity ciudad, String direccion);

    
    boolean existsByCiudadIdAndLatAndLng(Long ciudadId, BigDecimal lat, BigDecimal lng);

    
}

