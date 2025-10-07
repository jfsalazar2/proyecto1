package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculoService {
  private final VehiculoRepository repo;
  public VehiculoService(VehiculoRepository repo) { this.repo = repo; }
  public VehiculoEntity crear(VehiculoEntity e) { return repo.save(e); }
  public VehiculoEntity actualizar(String id, VehiculoEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(String id) { repo.deleteById(id); }
  public VehiculoEntity buscar(String id) { return repo.findById(id).orElse(null); }
  public List<VehiculoEntity> listar() { return repo.findAll(); }
}
