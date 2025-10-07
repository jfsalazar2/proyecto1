package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;
import uniandes.edu.co.proyecto.entities.enums.NivelVehiculo;

@Data
@Entity
public class TransporteEntity extends ServicioEntity {

    @Enumerated(EnumType.STRING)
    private NivelVehiculo nivelVehiculo; 

    @PodamExclude
    @ManyToOne
    private PuntoGeograficoEntity destino;
}


