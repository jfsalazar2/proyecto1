package uniandes.edu.co.proyecto.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record ServicioResumenDTO(
        Long id, String tipo, Integer costo, Integer duracion,
        LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
        Long conductorId, String conductorNombre,
        Long vehiculoId, String placa,
        Long origenId, String origenNombre,
        Long destinoId, String destinoNombre
) {}



