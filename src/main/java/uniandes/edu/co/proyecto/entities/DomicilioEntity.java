package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class DomicilioEntity extends ServicioEntity {
    // No atributos extra en el UML



    @PodamExclude
    @ManyToOne
    private PuntoGeograficoEntity destino;
}
