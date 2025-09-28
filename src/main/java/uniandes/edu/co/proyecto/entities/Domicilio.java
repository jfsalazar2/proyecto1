package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="DOMICILIOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Domicilio {
  @Id @Column(name="ID_SERVICIO") private Long idServicio; // PK = FK a SERVICIOS.ID
  @Column(name="DESCRIPCION") private String descripcion;
}
