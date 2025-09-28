package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="SERVICIOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Servicio {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID") private Long id;

  @Column(name="CEDULA_USUARIO_CONDUCTOR", nullable=false) private Long cedulaConductor;
  @Column(name="CEDULA_USUARIO_SERVICIO", nullable=false) private Long cedulaPasajero;
  @Column(name="PLACA_VEHICULO", nullable=false) private String placaVehiculo;
  @Column(name="TIPO", nullable=false) private String tipo; // transporte | domicilio | mercancia
  @Column(name="FECHA", nullable=false) private LocalDateTime fecha;
  @Column(name="LONGITUD_SERVICIO_KM") private Double longitudKm;
  @Column(name="CIUDAD") private String ciudad;
}
