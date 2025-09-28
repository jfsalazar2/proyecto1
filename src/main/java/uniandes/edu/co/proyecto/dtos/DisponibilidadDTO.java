package uniandes.edu.co.proyecto.dtos;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DisponibilidadDTO {
  private Long id;
  private Long cedulaConductor;
  private String placaVehiculo;
  private String dia;
  private LocalDateTime horaInicio;
  private LocalDateTime horaFin;
}
