package uniandes.edu.co.proyecto.dtos;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TransporteDetailDTO extends TransporteDTO {

    private List<PuntoGeograficoDTO> puntoGeografico = new ArrayList<>();


    
}
