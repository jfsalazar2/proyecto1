package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.VehiculoDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.services.VehiculoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
  private final VehiculoService service;
  public VehiculoController(VehiculoService service) { this.service = service; }

  @PostMapping
  public VehiculoDTO crear(@RequestBody VehiculoDTO dto) { 
    VehiculoEntity saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public VehiculoDTO actualizar(@PathVariable String id, @RequestBody VehiculoDTO dto){
    VehiculoEntity updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable String id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public VehiculoDTO buscar(@PathVariable String id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<VehiculoDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}

