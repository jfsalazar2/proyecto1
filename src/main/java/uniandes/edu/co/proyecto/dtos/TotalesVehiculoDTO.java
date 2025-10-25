package uniandes.edu.co.proyecto.dtos;

public record TotalesVehiculoDTO(Long vehiculoId, String placa,
                                 String tipoServicio, Long cantidad,
                                 Long bruto, Long neto) {}
