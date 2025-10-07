package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.ServicioDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.services.ServicioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
  private final ServicioService service;
  public ServicioController(ServicioService service) { this.service = service; }

  @PostMapping
  public ServicioDTO crear(@RequestBody ServicioDTO dto) { 
    ServicioEntity saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public ServicioDTO actualizar(@PathVariable Long id, @RequestBody ServicioDTO dto){
    ServicioEntity updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public ServicioDTO buscar(@PathVariable Long id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<ServicioDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}

