package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="VEHICULOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vehiculo {
  @Id @Column(name="PLACA") private String placa;
  @Column(name="MARCA") private String marca;
  @Column(name="MODELO") private Integer modelo;
  @Column(name="CAPACIDAD") private Integer capacidad;
  @Column(name="TIPO_VEHICULO") private String tipoVehiculo;
  @Column(name="CIUDAD_EXP_PLACA") private String ciudadExpPlaca;
}

