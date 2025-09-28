package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="TRANSPORTES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transporte {
  @Id @Column(name="ID_SERVICIO") private Long idServicio; // PK = FK a SERVICIOS.ID
  @Column(name="CANTIDAD_PASAJEROS") private Integer cantidadPasajeros;
}
