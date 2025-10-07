package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;

@Data
@Entity
public class DisponibilidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    @ElementCollection
    private List<String> dias;     

    private String horario;        

    private String tipoServicio;   

    @PodamExclude
    @ManyToOne
    private UsuarioConductorEntity conductor;

    
    @PodamExclude
    @ManyToOne
    private VehiculoEntity vehiculo;
}