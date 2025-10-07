package uniandes.edu.co.proyecto.dtos;
import lombok.Data;

@Data
public class VehiculoDTO {
  private Long id;
  private String modelo;
  private String marca;
  private String placa;
  private String color;
  private String ciudadExpPlaca;
  private int capacidad;
}

