package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.TransporteDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.Transporte;
import uniandes.edu.co.proyecto.services.TransporteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/transportes")
public class TransporteController {
  private final TransporteService service;
  public TransporteController(TransporteService service) { this.service = service; }

  @PostMapping
  public TransporteDTO crear(@RequestBody TransporteDTO dto) { 
    Transporte saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public TransporteDTO actualizar(@PathVariable Long id, @RequestBody TransporteDTO dto){
    Transporte updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public TransporteDTO buscar(@PathVariable Long id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<TransporteDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}
