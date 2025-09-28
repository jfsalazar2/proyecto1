package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.dtos.UsuarioServicioDTO;
import uniandes.edu.co.proyecto.dtos.Mappers;
import uniandes.edu.co.proyecto.entities.UsuarioServicio;
import uniandes.edu.co.proyecto.services.UsuarioServicioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static uniandes.edu.co.proyecto.dtos.Mappers.mapList;

@RestController
@RequestMapping("/api/usuarios-servicios")
public class UsuarioServicioController {
  private final UsuarioServicioService service;
  public UsuarioServicioController(UsuarioServicioService service) { this.service = service; }

  @PostMapping
  public UsuarioServicioDTO crear(@RequestBody UsuarioServicioDTO dto) { 
    UsuarioServicio saved = service.crear(Mappers.toEntity(dto));
    return Mappers.toDTO(saved);
  }

  @PutMapping("/{id}")
  public UsuarioServicioDTO actualizar(@PathVariable Long id, @RequestBody UsuarioServicioDTO dto){
    UsuarioServicio updated = service.actualizar(id, Mappers.toEntity(dto));
    return Mappers.toDTO(updated);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable Long id) { service.eliminar(id); }

  @GetMapping("/{id}")
  public UsuarioServicioDTO buscar(@PathVariable Long id) { 
    return Mappers.toDTO(service.buscar(id)); 
  }

  @GetMapping
  public List<UsuarioServicioDTO> listar() { 
    return mapList(service.listar(), Mappers::toDTO);
  }
}

