package uniandes.edu.co.proyecto.dtos;
import lombok.Data;

@Data
public class UsuarioDTO {
  private Long id;
  private String nombre;
  private String correo;
  private String telefono;
  private String cedula;
}