package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="NIVELES_DE_VEHICULOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NivelVehiculo {
  @Id @Column(name="NIVEL") private String nivel;
  @Column(name="TARIFA_DEL_NIVEL") private Double tarifaDelNivel;
}
