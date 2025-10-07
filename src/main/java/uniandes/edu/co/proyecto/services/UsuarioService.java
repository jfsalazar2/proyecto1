package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
  private final UsuarioRepository repo;
  public UsuarioService(UsuarioRepository repo) { this.repo = repo; }
  public UsuarioEntity crear(UsuarioEntity e) { return repo.save(e); }
  public UsuarioEntity actualizar(Long id, UsuarioEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public UsuarioEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<UsuarioEntity> listar() { return repo.findAll(); }
}
