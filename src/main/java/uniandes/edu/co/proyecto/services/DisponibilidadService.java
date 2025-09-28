package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.entities.Disponibilidad;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DisponibilidadService {
  private final DisponibilidadRepository repo;
  public DisponibilidadService(DisponibilidadRepository repo) { this.repo = repo; }
  public Disponibilidad crear(Disponibilidad e) { return repo.save(e); }
  public Disponibilidad actualizar(Long id, Disponibilidad e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public Disponibilidad buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<Disponibilidad> listar() { return repo.findAll(); }
}
