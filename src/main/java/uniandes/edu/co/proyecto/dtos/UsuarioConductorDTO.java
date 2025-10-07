package uniandes.edu.co.proyecto.dtos;

import lombok.Data;

@Data
public class UsuarioConductorDTO extends UsuarioDTO {
    private Long id;
    private boolean disponible;
    
}
