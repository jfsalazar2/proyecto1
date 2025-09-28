package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.ResenaDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.Resena;
import uniandes.edu.co.proyecto.services.ResenaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
  private final ResenaService service;
  public ResenaController(ResenaService service) { this.service = service; }

  @PostMapping
  public ResenaDTO crear(@RequestBody ResenaDTO dto) { 
    Resena saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public ResenaDTO actualizar(@PathVariable Long id, @RequestBody ResenaDTO dto){
    Resena updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public ResenaDTO buscar(@PathVariable Long id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<ResenaDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}

