package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="MERCANCIAS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Mercancia {
  @Id @Column(name="ID_SERVICIO") private Long idServicio; // PK = FK a SERVICIOS.ID
  @Column(name="PESO_KG") private Double pesoKg;
  @Column(name="VOLUMEN_M3") private Double volumenM3;
}
