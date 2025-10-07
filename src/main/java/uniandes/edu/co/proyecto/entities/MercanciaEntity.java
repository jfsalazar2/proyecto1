package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class MercanciaEntity extends ServicioEntity {
    private String descripcion;



    @PodamExclude
    @ManyToOne
    private PuntoGeograficoEntity destino;
}
