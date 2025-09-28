package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.entities.Usuario;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
  private final UsuarioRepository repo;
  public UsuarioService(UsuarioRepository repo) { this.repo = repo; }
  public Usuario crear(Usuario e) { return repo.save(e); }
  public Usuario actualizar(Long id, Usuario e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public Usuario buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<Usuario> listar() { return repo.findAll(); }
}
