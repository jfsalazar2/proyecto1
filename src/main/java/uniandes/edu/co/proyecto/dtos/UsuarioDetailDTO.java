package uniandes.edu.co.proyecto.dtos;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data

public class UsuarioDetailDTO extends UsuarioDTO{

    private List<CiudadDTO> ciudad = new ArrayList<>();
    private List<ResenaDTO> resena = new ArrayList<>();
    private List<ServicioDTO> servicio = new ArrayList<>();


    
}
