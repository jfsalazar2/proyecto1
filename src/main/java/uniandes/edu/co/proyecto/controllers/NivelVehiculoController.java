package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.NivelVehiculoDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.NivelVehiculo;
import uniandes.edu.co.proyecto.services.NivelVehiculoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/niveles-vehiculo")
public class NivelVehiculoController {
  private final NivelVehiculoService service;
  public NivelVehiculoController(NivelVehiculoService service) { this.service = service; }

  @PostMapping
  public NivelVehiculoDTO crear(@RequestBody NivelVehiculoDTO dto) { 
    NivelVehiculo saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public NivelVehiculoDTO actualizar(@PathVariable String id, @RequestBody NivelVehiculoDTO dto){
    NivelVehiculo updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable String id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public NivelVehiculoDTO buscar(@PathVariable String id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<NivelVehiculoDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}

