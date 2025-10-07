package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class ResenaEntity {

    

    private Integer calificacion; // 1..5
    private String comentario;

    @PodamExclude
    @ManyToOne
    private ServicioEntity servicio;

    @PodamExclude
    @ManyToOne
    private UsuarioServicioEntity autor;

    @PodamExclude
    @ManyToOne
    private UsuarioConductorEntity conductorResenado;

}
