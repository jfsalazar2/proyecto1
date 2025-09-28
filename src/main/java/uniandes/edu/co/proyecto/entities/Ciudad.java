package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="CIUDADES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ciudad {
  @Id @Column(name="NOMBRE") private String nombre;
  @Column(name="DEPARTAMENTO") private String departamento;
  @Column(name="PAIS") private String pais;
}
