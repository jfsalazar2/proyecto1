package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.entities.Vehiculo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculoService {
  private final VehiculoRepository repo;
  public VehiculoService(VehiculoRepository repo) { this.repo = repo; }
  public Vehiculo crear(Vehiculo e) { return repo.save(e); }
  public Vehiculo actualizar(String id, Vehiculo e) { 
    
    return repo.save(e); 
  }
  public void eliminar(String id) { repo.deleteById(id); }
  public Vehiculo buscar(String id) { return repo.findById(id).orElse(null); }
  public List<Vehiculo> listar() { return repo.findAll(); }
}
