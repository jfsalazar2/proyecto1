package uniandes.edu.co.proyecto.dtos;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UsuarioConductorDetailDTO extends UsuarioConductorDTO {

    private List<DisponibilidadDTO> disponibilidad = new ArrayList<>();
    private List<ServicioDTO> servicio = new ArrayList<>();
    private List<VehiculoDTO> vehiculo = new ArrayList<>();

    
}
