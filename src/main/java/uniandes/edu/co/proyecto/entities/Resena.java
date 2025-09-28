package uniandes.edu.co.proyecto.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="RESENAS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resena {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID") private Long id;
  @Column(name="CEDULA_USUARIO_AUTOR", nullable=false) private Long cedulaAutor;
  @Column(name="CEDULA_USUARIO_RECEPTOR", nullable=false) private Long cedulaReceptor;
  @Column(name="CALIFICACION", nullable=false) private Integer calificacion; // 0..5
  @Column(name="COMENTARIO") private String comentario;
  @Column(name="FECHA", nullable=false) private LocalDateTime fecha;
}
