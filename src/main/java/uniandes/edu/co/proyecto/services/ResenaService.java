package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.ResenaRepository;
import uniandes.edu.co.proyecto.entities.ResenaEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResenaService {
  private final ResenaRepository repo;
  public ResenaService(ResenaRepository repo) { this.repo = repo; }
  public ResenaEntity crear(ResenaEntity e) { return repo.save(e); }
  public ResenaEntity actualizar(Long id, ResenaEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public ResenaEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<ResenaEntity> listar() { return repo.findAll(); }
}