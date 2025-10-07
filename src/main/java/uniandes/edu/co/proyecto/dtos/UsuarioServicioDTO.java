package uniandes.edu.co.proyecto.dtos;
import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioServicioDTO extends UsuarioDTO {
  private Long id;
  private String nombreTarjeta;
  private String fechaVencimiento;
  private String cvc;
  private String numerTarjeta;
  private boolean disponible;
}
