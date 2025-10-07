package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {
    List<ServicioEntity> findByCiudadAndFecha(CiudadEntity ciudad, LocalDate fecha);
    List<ServicioEntity> findByCliente(UsuarioServicioEntity cliente);
    List<ServicioEntity> findByConductor(UsuarioConductorEntity conductor);
    List<ServicioEntity> findByEstado(Boolean estado);
}