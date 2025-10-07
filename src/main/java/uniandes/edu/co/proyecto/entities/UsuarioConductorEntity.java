package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UsuarioConductorEntity extends UsuarioEntity {

    private boolean disponible;

    @PodamExclude
    @OneToMany(mappedBy = "conductor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ServicioEntity> serviciosRealizados = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VehiculoEntity> vehiculos = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "conductor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<DisponibilidadEntity> disponibilidades = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "conductorResenado", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ResenaEntity> resenasRecibidas = new ArrayList<>();
}
