package uniandes.edu.co.proyecto.dtos;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class VehiculoDetailDTO extends VehiculoDTO {

    private List<DisponibilidadDTO> disponibilidad = new ArrayList<>();
    private List<ServicioDTO> servicio = new ArrayList<>();
    private List<UsuarioConductorDTO> usuarioConductor = new ArrayList<>();
    private List<CiudadDTO> ciudad = new ArrayList<>();


    
}
