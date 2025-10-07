package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioServicioService {
  private final UsuarioServicioRepository repo;
  public UsuarioServicioService(UsuarioServicioRepository repo) { this.repo = repo; }
  public UsuarioServicioEntity crear(UsuarioServicioEntity e) { return repo.save(e); }
  public UsuarioServicioEntity actualizar(Long id, UsuarioServicioEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public UsuarioServicioEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<UsuarioServicioEntity> listar() { return repo.findAll(); }
}