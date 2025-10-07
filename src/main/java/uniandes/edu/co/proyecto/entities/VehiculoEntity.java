package uniandes.edu.co.proyecto.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;
import uniandes.edu.co.proyecto.entities.enums.TipoVehiculo;

@Data
@Entity
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    

    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipo; // CARRO, MOTOCICLETA, CAMIONETA

    private String modelo;
    private String marca;

    private String placa;

    private String color;
    private String ciudadExpPlaca;
    private Integer capacidad;

    @PodamExclude
    @ManyToOne
    private UsuarioConductorEntity propietario;

    @PodamExclude
    @ManyToOne
    private CiudadEntity ciudad;

    
    @PodamExclude
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<DisponibilidadEntity> disponibilidades = new ArrayList<>();
  
}

