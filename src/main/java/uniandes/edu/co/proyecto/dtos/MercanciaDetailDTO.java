package uniandes.edu.co.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MercanciaDetailDTO extends MercanciaDTO {

    private List<PuntoGeograficoDTO> puntoGeografico = new ArrayList<>();


    
}
