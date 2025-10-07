package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public abstract class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    

    private String nombre;
    private String correo;
    private String telefono;

    @Column(unique = true)
    private String cedula; 

    @PodamExclude
    @ManyToOne
    private CiudadEntity ciudad;

}
