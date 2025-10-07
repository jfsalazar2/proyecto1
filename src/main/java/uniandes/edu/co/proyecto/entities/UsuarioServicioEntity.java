package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import uk.co.jemos.podam.common.PodamExclude;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UsuarioServicioEntity extends UsuarioEntity {
    private String nombreTarjeta;
    private String fechaVencimiento; // MM/AA
    private String cvc;
    private String numeroTarjeta;

    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ServicioEntity> serviciosSolicitados = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ResenaEntity> resenas = new ArrayList<>();
  
}