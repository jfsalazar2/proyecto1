package uniandes.edu.co.proyecto.dtos;

import lombok.Data;

@Data
public class PuntoGeograficoDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String coordenadas; 
    
}
