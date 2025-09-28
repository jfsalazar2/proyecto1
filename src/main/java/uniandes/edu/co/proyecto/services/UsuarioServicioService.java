package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;
import uniandes.edu.co.proyecto.entities.UsuarioServicio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioServicioService {
  private final UsuarioServicioRepository repo;
  public UsuarioServicioService(UsuarioServicioRepository repo) { this.repo = repo; }
  public UsuarioServicio crear(UsuarioServicio e) { return repo.save(e); }
  public UsuarioServicio actualizar(Long id, UsuarioServicio e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public UsuarioServicio buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<UsuarioServicio> listar() { return repo.findAll(); }
}