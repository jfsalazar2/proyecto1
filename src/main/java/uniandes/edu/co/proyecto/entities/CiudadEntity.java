package uniandes.edu.co.proyecto.entities;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class CiudadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    private String nombre;

  
  
  @PodamExclude
    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VehiculoEntity> vehiculos = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ServicioEntity> servicios = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PuntoGeograficoEntity> destinos = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UsuarioEntity> usuarios = new ArrayList<>();

}
