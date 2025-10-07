package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.TransporteRepository;
import uniandes.edu.co.proyecto.entities.TransporteEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransporteService {
  private final TransporteRepository repo;
  public TransporteService(TransporteRepository repo) { this.repo = repo; }
  public TransporteEntity crear(TransporteEntity e) { return repo.save(e); }
  public TransporteEntity actualizar(Long id, TransporteEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public TransporteEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<TransporteEntity> listar() { return repo.findAll(); }
}
