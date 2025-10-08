package uniandes.edu.co.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data

public class PuntoGeograficoDetailDTO extends PuntoGeograficoDTO {

    private List<CiudadDTO> ciudad = new ArrayList<>();
    private List<ServicioDTO> servicio = new ArrayList<>();
    private List<MercanciaDTO> mercancia = new ArrayList<>();
    private List<DomicilioDTO> domicilio = new ArrayList<>();
    private List<TransporteDTO> transporte = new ArrayList<>();

    
}
