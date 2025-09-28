package uniandes.edu.co.proyecto.repositories;
import uniandes.edu.co.proyecto.entities.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

  // RFC1 ejemplo: ingresos por vehiculo
  @Query(value = """
    SELECT PLACA_VEHICULO, SUM(DINERO_CONDUCTOR) AS TOTAL
    FROM V_COSTOS_SERVICIOS
    GROUP BY PLACA_VEHICULO
  """, nativeQuery = true)
  List<Object[]> rfcIngresosPorVehiculo();

  // RFC2 ejemplo: ingresos por tipo de servicio
  @Query(value = """
    SELECT TIPO_SERVICIO, SUM(TARIFA_BASE * NVL(LONGITUD_SERVICIO_KM,1)) AS TOTAL
    FROM V_COSTOS_SERVICIOS_AGREGADO
    GROUP BY TIPO_SERVICIO
  """, nativeQuery = true)
  List<Object[]> rfcIngresosPorTipo();

  // RFC3 ejemplo: top vehiculos en rango de fechas y ciudad
  @Query(value = """
    SELECT S.PLACA_VEHICULO, SUM(VCS.DINERO_CONDUCTOR) AS TOTAL
    FROM SERVICIOS S
    JOIN V_COSTOS_SERVICIOS VCS ON VCS.ID_SERVICIO = S.ID
    WHERE (:ciudad IS NULL OR S.CIUDAD = :ciudad)
      AND TRUNC(S.FECHA) BETWEEN :desde AND :hasta
    GROUP BY S.PLACA_VEHICULO
    ORDER BY TOTAL DESC
  """, nativeQuery = true)
  List<Object[]> rfcTopVehiculos(@Param("ciudad") String ciudad,
                                 @Param("desde") java.sql.Date desde,
                                 @Param("hasta") java.sql.Date hasta);
}
