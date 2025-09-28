package uniandes.edu.co.proyecto.dtos;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CiudadDTO {
  private String nombre;
  private String departamento;
  private String pais;
}