package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="DISPONIBILIDADES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Disponibilidad {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID") private Long id;
  @Column(name="CEDULA_USUARIO_CONDUCTOR", nullable=false) private Long cedulaConductor;
  @Column(name="PLACA_VEHICULO", nullable=false) private String placaVehiculo;
  @Column(name="DIA", nullable=false) private String dia;
  @Column(name="HORA_INICIO", nullable=false) private LocalDateTime horaInicio;
  @Column(name="HORA_FIN", nullable=false) private LocalDateTime horaFin;
}