package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.TipoServicioRepository;
import uniandes.edu.co.proyecto.entities.TipoServicio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoServicioService {
  private final TipoServicioRepository repo;
  public TipoServicioService(TipoServicioRepository repo) { this.repo = repo; }
  public TipoServicio crear(TipoServicio e) { return repo.save(e); }
  public TipoServicio actualizar(String id, TipoServicio e) { 
    
    return repo.save(e); 
  }
  public void eliminar(String id) { repo.deleteById(id); }
  public TipoServicio buscar(String id) { return repo.findById(id).orElse(null); }
  public List<TipoServicio> listar() { return repo.findAll(); }
}

