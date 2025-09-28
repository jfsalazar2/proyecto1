package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name="USUARIOS_SERVICIOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioServicio {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID") private Long id;
  @Column(name="CEDULA_USUARIO", nullable=false) private Long cedulaUsuario;
  @Column(name="NUM_TARJETA", nullable=false) private String numTarjeta;
  @Column(name="FECHA_EXP_TARJETA", nullable=false) private LocalDate fechaExpiracion;
  @Column(name="FRANQUICIA", nullable=false) private String franquicia;
}