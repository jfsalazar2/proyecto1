package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.ResenaRepository;
import uniandes.edu.co.proyecto.entities.Resena;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResenaService {
  private final ResenaRepository repo;
  public ResenaService(ResenaRepository repo) { this.repo = repo; }
  public Resena crear(Resena e) { return repo.save(e); }
  public Resena actualizar(Long id, Resena e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public Resena buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<Resena> listar() { return repo.findAll(); }
}