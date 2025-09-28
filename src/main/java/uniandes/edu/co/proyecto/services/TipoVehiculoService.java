package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.TipoVehiculoRepository;
import uniandes.edu.co.proyecto.entities.TipoVehiculo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoVehiculoService {
  private final TipoVehiculoRepository repo;
  public TipoVehiculoService(TipoVehiculoRepository repo) { this.repo = repo; }
  public TipoVehiculo crear(TipoVehiculo e) { return repo.save(e); }
  public TipoVehiculo actualizar(String id, TipoVehiculo e) { 
    
    return repo.save(e); 
  }
  public void eliminar(String id) { repo.deleteById(id); }
  public TipoVehiculo buscar(String id) { return repo.findById(id).orElse(null); }
  public List<TipoVehiculo> listar() { return repo.findAll(); }
}
