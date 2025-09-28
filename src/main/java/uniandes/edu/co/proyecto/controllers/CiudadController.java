package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.CiudadDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.Ciudad;
import uniandes.edu.co.proyecto.services.CiudadService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {
  private final CiudadService service;
  public CiudadController(CiudadService service) { this.service = service; }

  @PostMapping
  public CiudadDTO crear(@RequestBody CiudadDTO dto) { 
    Ciudad saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public CiudadDTO actualizar(@PathVariable String id, @RequestBody CiudadDTO dto){
    Ciudad updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable String id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public CiudadDTO buscar(@PathVariable String id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<CiudadDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}
