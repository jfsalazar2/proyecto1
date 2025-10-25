package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Object, Long> {

    @Query(value = """
       select case when count(1) > 0 then 1 else 0 end
         from MEDIO_PAGO mp
        where mp.USUARIO_ID = :clienteId
          and mp.ACTIVO = 1
    """, nativeQuery = true)
    int clienteTieneMedioPagoActivo(@Param("clienteId") Long clienteId);
}
