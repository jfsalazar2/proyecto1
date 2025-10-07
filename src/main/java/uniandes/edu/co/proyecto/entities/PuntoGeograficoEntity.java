package uniandes.edu.co.proyecto.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
@Data
@Entity
public class PuntoGeograficoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  


    private String nombre;
    private String coordenadas; 
    private String direccion;

    @PodamExclude
    @ManyToOne
    private CiudadEntity ciudad;

    @PodamExclude
    @OneToMany(mappedBy = "origen", cascade = CascadeType.PERSIST)
    private List<DomicilioEntity> domiciliosOrigen = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "origen", cascade = CascadeType.PERSIST)
    private List<MercanciaEntity> mercanciasOrigen = new ArrayList<>();
}
