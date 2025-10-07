package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.MercanciaDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.MercanciaEntity;
import uniandes.edu.co.proyecto.services.MercanciaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/mercancias")
public class MercanciaController {
  private final MercanciaService service;
  public MercanciaController(MercanciaService service) { this.service = service; }

  @PostMapping
  public MercanciaDTO crear(@RequestBody MercanciaDTO dto) { 
    MercanciaEntity saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public MercanciaDTO actualizar(@PathVariable Long id, @RequestBody MercanciaDTO dto){
    MercanciaEntity updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public MercanciaDTO buscar(@PathVariable Long id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<MercanciaDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}

