package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioService {
  private final ServicioRepository repo;
  public ServicioService(ServicioRepository repo) { this.repo = repo; }
  public ServicioEntity crear(ServicioEntity e) { return repo.save(e); }
  public ServicioEntity actualizar(Long id, ServicioEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public ServicioEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<ServicioEntity> listar() { return repo.findAll(); }
}
