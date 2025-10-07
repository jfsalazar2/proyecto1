package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.DomicilioRepository;
import uniandes.edu.co.proyecto.entities.DomicilioEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DomicilioService {
  private final DomicilioRepository repo;
  public DomicilioService(DomicilioRepository repo) { this.repo = repo; }
  public DomicilioEntity crear(DomicilioEntity e) { return repo.save(e); }
  public DomicilioEntity actualizar(Long id, DomicilioEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public DomicilioEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<DomicilioEntity> listar() { return repo.findAll(); }
}
