package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="TIPOS_VEHICULOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoVehiculo {
  @Id @Column(name="TIPO") private String tipo;
  @Column(name="DESCRIPCION") private String descripcion;
}
