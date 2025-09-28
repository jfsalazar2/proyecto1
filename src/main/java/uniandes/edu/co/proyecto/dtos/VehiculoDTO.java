package uniandes.edu.co.proyecto.dtos;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehiculoDTO {
  private String placa;
  private String marca;
  private Integer modelo;
  private Integer capacidad;
  private String tipoVehiculo;
  private String ciudadExpPlaca;
}

