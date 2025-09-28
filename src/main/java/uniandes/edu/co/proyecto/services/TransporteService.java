package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.TransporteRepository;
import uniandes.edu.co.proyecto.entities.Transporte;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransporteService {
  private final TransporteRepository repo;
  public TransporteService(TransporteRepository repo) { this.repo = repo; }
  public Transporte crear(Transporte e) { return repo.save(e); }
  public Transporte actualizar(Long id, Transporte e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public Transporte buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<Transporte> listar() { return repo.findAll(); }
}
