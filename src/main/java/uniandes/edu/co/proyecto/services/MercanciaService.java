package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.MercanciaRepository;
import uniandes.edu.co.proyecto.entities.Mercancia;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MercanciaService {
  private final MercanciaRepository repo;
  public MercanciaService(MercanciaRepository repo) { this.repo = repo; }
  public Mercancia crear(Mercancia e) { return repo.save(e); }
  public Mercancia actualizar(Long id, Mercancia e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public Mercancia buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<Mercancia> listar() { return repo.findAll(); }
}
