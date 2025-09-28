package uniandes.edu.co.proyecto.dtos;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServicioDTO {
  private Long id;
  private Long cedulaConductor;
  private Long cedulaPasajero;
  private String placaVehiculo;
  private String tipo;
  private LocalDateTime fecha;
  private Double longitudKm;
  private String ciudad;
}
