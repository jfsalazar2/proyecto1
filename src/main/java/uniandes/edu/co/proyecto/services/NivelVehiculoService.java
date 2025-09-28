package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.NivelVehiculoRepository;
import uniandes.edu.co.proyecto.entities.NivelVehiculo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NivelVehiculoService {
  private final NivelVehiculoRepository repo;
  public NivelVehiculoService(NivelVehiculoRepository repo) { this.repo = repo; }
  public NivelVehiculo crear(NivelVehiculo e) { return repo.save(e); }
  public NivelVehiculo actualizar(String id, NivelVehiculo e) { 
    
    return repo.save(e); 
  }
  public void eliminar(String id) { repo.deleteById(id); }
  public NivelVehiculo buscar(String id) { return repo.findById(id).orElse(null); }
  public List<NivelVehiculo> listar() { return repo.findAll(); }
}
