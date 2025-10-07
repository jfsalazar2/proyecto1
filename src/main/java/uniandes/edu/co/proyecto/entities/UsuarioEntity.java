package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public abstract class UsuarioEntity {

    

    private String nombre;
    private String correo;
    private String telefono;

    @PodamExclude
    @ManyToOne
    private CiudadEntity ciudad;

}
