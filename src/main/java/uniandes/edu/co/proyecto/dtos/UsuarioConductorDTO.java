package uniandes.edu.co.proyecto.dtos;

import lombok.Data;

@Data
public class UsuarioConductorDTO extends UsuarioDTO {
    private Long id;
    private boolean disponible;
    public String nombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nombre'");
    }
    
}
