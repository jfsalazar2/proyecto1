package uniandes.edu.co.proyecto.dtos;
import lombok.*;
import java.time.LocalDate;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioServicioDTO {
  private Long id;
  private Long cedulaUsuario;
  private String numTarjeta;
  private LocalDate fechaExpiracion;
  private String franquicia;
}
