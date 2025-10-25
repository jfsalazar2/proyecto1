package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {
    List<ServicioEntity> findByCiudadAndFecha(CiudadEntity ciudad, LocalDate fecha);
    List<ServicioEntity> findByCliente(UsuarioServicioEntity cliente);
    List<ServicioEntity> findByConductor(UsuarioConductorEntity conductor);
    List<ServicioEntity> findByEstado(Boolean estado);

    boolean existsByConductorIdAndEstadoTrue(Long conductorId);

    
    List<ServicioEntity> findByClienteIdOrderByFechaDescHoraInicioDesc(Long clienteId);

    
    @Query("""
        select c.id, c.nombre, count(s) as total
        from ServicioEntity s
        join s.conductor c
        where s.estado = true
        group by c.id, c.nombre
        order by total desc
    """)
    List<Object[]> topConductores(Pageable pageable);

    
    @Query("""
        select v.id, v.placa, s.tipo, count(s), coalesce(sum(s.costo), 0)
        from ServicioEntity s
        join s.vehiculo v
        where s.conductor.id = :conductorId
          and s.estado = true
        group by v.id, v.placa, s.tipo
        order by v.placa asc, s.tipo asc
    """)
    List<Object[]> totalesPorVehiculoYTipo(@Param("conductorId") Long conductorId);

    
    @Query("""
        select s.tipo,
               (case when type(s) = TransporteEntity then (t.nivelVehiculo) else null end) as nivel,
               count(s) as total
        from ServicioEntity s
        left join treat(s as TransporteEntity) t
        where s.ciudad.id = :ciudadId
          and s.fecha between :ini and :fin
        group by s.tipo, (case when type(s) = TransporteEntity then (t.nivelVehiculo) else null end)
        order by total desc
    """)
    List<Object[]> usoServiciosPorCiudadYRango(@Param("ciudadId") Long ciudadId,
                                               @Param("ini") LocalDate ini,
                                               @Param("fin") LocalDate fin);

    
    @Query(value = """
        SELECT * 
          FROM SERVICIO
         WHERE CLIENTE_ID = :clienteId
         ORDER BY FECHA DESC, HORA_INICIO DESC
    """, nativeQuery = true)
    List<ServicioEntity> historicoClienteNative(@Param("clienteId") Long clienteId);
}