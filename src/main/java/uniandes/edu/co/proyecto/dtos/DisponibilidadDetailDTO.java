package uniandes.edu.co.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DisponibilidadDetailDTO extends DisponibilidadDTO {

    private List<ServicioDTO> servicio = new ArrayList<>();
    private List<UsuarioConductorDTO> usuarioConductor = new ArrayList<>();
    private List<VehiculoDTO> vehiculo = new ArrayList<>();


    
}
