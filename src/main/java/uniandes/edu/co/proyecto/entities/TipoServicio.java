package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="TIPOS_SERVICIOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoServicio {
  @Id @Column(name="TIPO_SERVICIO") private String tipoServicio;
  @Column(name="TARIFA") private Double tarifa;
}
