package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DisponibilidadRepository extends JpaRepository<DisponibilidadEntity, Long> {

    
    List<DisponibilidadEntity> findByConductor(UsuarioConductorEntity conductor);

    
    @Query("""
        SELECT d 
        FROM DisponibilidadEntity d
        WHERE UPPER(d.tipoServicio) = UPPER(:tipoServicio)
          AND :dia MEMBER OF d.dias
    """)
    List<DisponibilidadEntity> findActivasPorTipoServicioYDia(@Param("tipoServicio") String tipoServicio,
                                                              @Param("dia") DayOfWeek dia);

    
    @Query("""
        SELECT d
        FROM DisponibilidadEntity d
        WHERE d.conductor = :conductor
          AND :dia MEMBER OF d.dias
          AND (d.horaInicio < :fin AND d.horaFin > :inicio)
    """)
    List<DisponibilidadEntity> buscarTraslapes(@Param("conductor") UsuarioConductorEntity conductor,
                                               @Param("dia") DayOfWeek dia,
                                               @Param("inicio") LocalTime inicio,
                                               @Param("fin") LocalTime fin);

    
    @Query("""
        SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
        FROM DisponibilidadEntity d
        WHERE d.conductor.id = :conductorId
          AND UPPER(d.tipoServicio) = UPPER(:tipoServicio)
          AND d.horario = :horario
    """)
    boolean existsByConductorAndTipoServicioAndHorario(@Param("conductorId") Long conductorId,
                                                       @Param("tipoServicio") String tipoServicio,
                                                       @Param("horario") String horario);

    


    boolean existsByConductorIdAndEstadoTrue(Long conductorId);

    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
      update DISPONIBILIDAD
         set EN_USO = 1
       where ID = :id
         and EN_USO = 0
    """, nativeQuery = true)
    int bloquearSiLibre(@Param("id") Long id);

    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update DISPONIBILIDAD set EN_USO = 0 where ID = :id", nativeQuery = true)
    int liberar(@Param("id") Long id);
}

