package uniandes.edu.co.proyecto.dtos;

import java.util.List;

import lombok.Data;

@Data

public class DisponibilidadDTO {
  private Long id;
  private List<String> dias;
  private String horario;
  private String tipoServicio;
}
