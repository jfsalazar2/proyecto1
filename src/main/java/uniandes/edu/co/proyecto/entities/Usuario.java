package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="USUARIOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {
  @Id @Column(name="CEDULA") private Long cedula;
  @Column(name="NOMBRE", nullable=false) private String nombre;
  @Column(name="EMAIL", nullable=false, unique=true) private String email;
  @Column(name="TELEFONO", unique=true) private String telefono;
  @Column(name="ROL", nullable=false) private String rol;
}
