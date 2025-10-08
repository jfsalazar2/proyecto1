package uniandes.edu.co.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data

public class ServicioDetailDTO extends ServicioDTO{

    private List<CiudadDTO> ciudad = new ArrayList<>();
    private List<PuntoGeograficoDTO> puntoGeografico = new ArrayList<>();
    private List<UsuarioDTO> usuario = new ArrayList<>();
    private List<UsuarioConductorDTO> usuarioConductor = new ArrayList<>();
    private List<UsuarioServicioDTO> usuarioServicio = new ArrayList<>();
    private List<ResenaDTO> resena = new ArrayList<>();
    private List<VehiculoDTO> vehiculo = new ArrayList<>();


    
}
