package uniandes.edu.co.proyecto.dtos;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UsuarioServicioDetailDTO extends UsuarioServicioDTO {

    private List<ServicioDTO> servicio = new ArrayList<>();


    
}
