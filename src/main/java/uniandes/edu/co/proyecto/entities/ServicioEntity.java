package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;

import java.time.LocalDate;

import java.time.LocalTime;

@Data
@Entity

public abstract class ServicioEntity {


    
    private String tipo;                 // (p.ej. "Transporte", "Domicilio", "Mercancia")
    private Integer tarifaKm;
    private Integer longitudServicio;    // en km (o metros, según convenga)
    private Boolean estado;              // activo/finalizado
    private Integer costo;
    private Integer duracion;            // en minutos

    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fecha;

    @PodamExclude
    @ManyToOne
    private UsuarioServicioEntity cliente;

    @PodamExclude
    @ManyToOne
    private UsuarioConductorEntity conductor;

    @PodamExclude
    @ManyToOne
    private VehiculoEntity vehiculo;

    @PodamExclude
    @ManyToOne
    private CiudadEntity ciudad;

    
    @PodamExclude
    @ManyToOne
    private PuntoGeograficoEntity origen;

    

    
}
