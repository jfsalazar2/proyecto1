package uniandes.edu.co.proyecto.dtos;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResenaDTO {
  private Long id;
  private Long cedulaAutor;
  private Long cedulaReceptor;
  private Integer calificacion;
  private String comentario;
  private LocalDateTime fecha;
}
