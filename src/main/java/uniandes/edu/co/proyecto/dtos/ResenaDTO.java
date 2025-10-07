package uniandes.edu.co.proyecto.dtos;
import lombok.Data;

@Data
public class ResenaDTO {
  private Long id;
  private int calificacion;
  private String comentarios;
}
