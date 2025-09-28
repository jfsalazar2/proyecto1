package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.entities.Servicio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioService {
  private final ServicioRepository repo;
  public ServicioService(ServicioRepository repo) { this.repo = repo; }
  public Servicio crear(Servicio e) { return repo.save(e); }
  public Servicio actualizar(Long id, Servicio e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public Servicio buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<Servicio> listar() { return repo.findAll(); }
}
