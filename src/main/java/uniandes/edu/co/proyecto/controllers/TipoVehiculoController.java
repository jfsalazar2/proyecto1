package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.TipoVehiculoDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.TipoVehiculo;
import uniandes.edu.co.proyecto.services.TipoVehiculoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/tipos-vehiculo")
public class TipoVehiculoController {
  private final TipoVehiculoService service;
  public TipoVehiculoController(TipoVehiculoService service) { this.service = service; }

  @PostMapping
  public TipoVehiculoDTO crear(@RequestBody TipoVehiculoDTO dto) { 
    TipoVehiculo saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public TipoVehiculoDTO actualizar(@PathVariable String id, @RequestBody TipoVehiculoDTO dto){
    TipoVehiculo updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable String id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public TipoVehiculoDTO buscar(@PathVariable String id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<TipoVehiculoDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}

