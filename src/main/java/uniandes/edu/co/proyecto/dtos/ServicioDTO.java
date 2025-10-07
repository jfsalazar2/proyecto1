package uniandes.edu.co.proyecto.dtos;
import java.sql.Date;
import lombok.Data;

@Data
public class ServicioDTO {
  private Long id;
  private String tipo;
  private String horaInicio;
  private int tarifaKm;
  private String horaFin;
  private Date fecha;
  private int longitudServicio;
  private boolean estado;
  private int costo;
  private int duracion;
  

}
