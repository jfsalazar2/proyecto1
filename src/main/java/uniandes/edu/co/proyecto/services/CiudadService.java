package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.entities.Ciudad;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CiudadService {
  private final CiudadRepository repo;
  public CiudadService(CiudadRepository repo) { this.repo = repo; }
  public Ciudad crear(Ciudad e) { return repo.save(e); }
  public Ciudad actualizar(String id, Ciudad e) { 
    
    return repo.save(e); 
  }
  public void eliminar(String id) { repo.deleteById(id); }
  public Ciudad buscar(String id) { return repo.findById(id).orElse(null); }
  public List<Ciudad> listar() { return repo.findAll(); }
}

