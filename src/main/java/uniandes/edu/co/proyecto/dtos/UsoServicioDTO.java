package uniandes.edu.co.proyecto.dtos;

import uniandes.edu.co.proyecto.entities.enums.NivelVehiculo;

public record UsoServicioDTO(String tipoServicio, NivelVehiculo nivel,
                             Long cantidad, double porcentaje) {}
