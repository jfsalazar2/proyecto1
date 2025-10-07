package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DisponibilidadService {
  private final DisponibilidadRepository repo;
  public DisponibilidadService(DisponibilidadRepository repo) { this.repo = repo; }
  public DisponibilidadEntity crear(DisponibilidadEntity e) { return repo.save(e); }
  public DisponibilidadEntity actualizar(Long id, DisponibilidadEntity e) { 
    
    return repo.save(e); 
  }
  public void eliminar(Long id) { repo.deleteById(id); }
  public DisponibilidadEntity buscar(Long id) { return repo.findById(id).orElse(null); }
  public List<DisponibilidadEntity> listar() { return repo.findAll(); }
}
