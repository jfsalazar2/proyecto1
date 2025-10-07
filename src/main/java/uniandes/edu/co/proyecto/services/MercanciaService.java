package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.MercanciaRepository;
import uniandes.edu.co.proyecto.entities.MercanciaEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MercanciaService {
  private final MercanciaRepository repo;
  public MercanciaService(MercanciaRepository repo) { this.repo = repo; }
  public MercanciaEntity crear(MercanciaEntity e) { return repo.save(e); }
  public MercanciaEntity actualizar(Long id, MercanciaEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public MercanciaEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<MercanciaEntity> listar() { return repo.findAll(); }
}
