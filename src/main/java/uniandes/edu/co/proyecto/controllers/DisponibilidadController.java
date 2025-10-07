package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.DisponibilidadDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.services.DisponibilidadService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {
  private final DisponibilidadService service;
  public DisponibilidadController(DisponibilidadService service) { this.service = service; }

  @PostMapping
  public DisponibilidadDTO crear(@RequestBody DisponibilidadDTO dto) { 
    DisponibilidadEntity saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public DisponibilidadDTO actualizar(@PathVariable Long id, @RequestBody DisponibilidadDTO dto){
    DisponibilidadEntity updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public DisponibilidadDTO buscar(@PathVariable Long id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<DisponibilidadDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}
