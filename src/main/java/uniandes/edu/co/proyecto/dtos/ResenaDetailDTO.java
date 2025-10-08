package uniandes.edu.co.proyecto.dtos;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data

public class ResenaDetailDTO extends ResenaDTO {

    private List<UsuarioDTO> usuario = new ArrayList<>();
    private List<ServicioDTO> servicio = new ArrayList<>();
    

    
}