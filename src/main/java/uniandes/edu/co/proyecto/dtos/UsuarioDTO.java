package uniandes.edu.co.proyecto.dtos;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioDTO {
  private Long cedula;
  private String nombre;
  private String email;
  private String telefono;
  private String rol;
}