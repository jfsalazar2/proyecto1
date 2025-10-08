package uniandes.edu.co.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CiudadDetailDTO extends CiudadDTO {

    private List<PuntoGeograficoDTO> puntoGeografico = new ArrayList<>();
    private List<ServicioDTO> servicio = new ArrayList<>();
    private List<UsuarioDTO> usuario = new ArrayList<>();
    private List<VehiculoDTO> vehiculo = new ArrayList<>();


    
}
