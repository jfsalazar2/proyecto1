package uniandes.edu.co.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DomicilioDetailDTO extends DomicilioDTO {

    private List<PuntoGeograficoDTO> puntoGeografico = new ArrayList<>();


    
}
