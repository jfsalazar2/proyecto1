package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.DomicilioRepository;
import uniandes.edu.co.proyecto.entities.Domicilio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DomicilioService {
  private final DomicilioRepository repo;
  public DomicilioService(DomicilioRepository repo) { this.repo = repo; }
  public Domicilio crear(Domicilio e) { return repo.save(e); }
  public Domicilio actualizar(Long id, Domicilio e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public Domicilio buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<Domicilio> listar() { return repo.findAll(); }
}
