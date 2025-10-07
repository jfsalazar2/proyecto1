package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CiudadService {
  private final CiudadRepository repo;
  public CiudadService(CiudadRepository repo) { this.repo = repo; }
  public CiudadEntity crear(CiudadEntity e) { return repo.save(e); }
  public CiudadEntity actualizar(String id, CiudadEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(String id) { repo.deleteById(id); }
  public CiudadEntity buscar(String id) { return repo.findById(id).orElse(null); }
  public List<CiudadEntity> listar() { return repo.findAll(); }
}

