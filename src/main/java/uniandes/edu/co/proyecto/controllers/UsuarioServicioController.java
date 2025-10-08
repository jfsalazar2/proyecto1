package uniandes.edu.co.proyecto.controllers;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import jakarta.persistence.EntityNotFoundException;
import uniandes.edu.co.proyecto.dtos.UsuarioServicioDTO;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.exceptions.IllegalOperationException;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;
import uniandes.edu.co.proyecto.services.UsuarioServicioService;


@RestController
@RequestMapping("/usuarios/servicio")
public class UsuarioServicioController {

    @Autowired
    private UsuarioServicioService usuarioServicioService;

    @Autowired
    private UsuarioServicioRepository usuarioServicioRepository; // <- Usamos repo para listados

    @Autowired
    private ModelMapper modelMapper;

    // Listar usando el repo (evita getUsuarios() en el service)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioServicioDTO> getUsuariosServicio() {
        List<UsuarioServicioEntity> entidades = usuarioServicioRepository.findAll();
        java.lang.reflect.Type listType = new TypeToken<List<UsuarioServicioDTO>>(){}.getType();
        return modelMapper.map(entidades, listType);
    }

    // Obtener por id (opcional, también vía repo)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioServicioDTO getUsuarioServicio(@PathVariable Long id) throws EntityNotFoundException {
        UsuarioServicioEntity e = usuarioServicioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe UsuarioServicio con id " + id));
        return modelMapper.map(e, UsuarioServicioDTO.class);
    }

    // RF2 - Registrar usuario de servicios
    // En tu DTO no existe ciudadId(), así que lo recibimos como query param:
    // POST /usuarios/servicio?ciudadId=123
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioServicioDTO crearUsuarioServicio(@RequestBody UsuarioServicioDTO dto,
                                                   @RequestParam("ciudadId") Long ciudadId)
            throws IllegalOperationException, EntityNotFoundException {

        var creado = usuarioServicioService.rf2RegistrarUsuarioServicio(
                dto.getNombre(), dto.getCorreo(), dto.getTelefono(), dto.getCedula(), ciudadId
        );
        return modelMapper.map(creado, UsuarioServicioDTO.class);
    }

    // Actualizar (si lo expones)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioServicioDTO updateUsuarioServicio(@PathVariable Long id,
                                                    @RequestBody UsuarioServicioDTO dto)
            throws EntityNotFoundException, IllegalOperationException {
        // Si tu service no tiene update, puedes mapear y guardar con repo.
        UsuarioServicioEntity existente = usuarioServicioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe UsuarioServicio con id " + id));

        // Actualizamos campos básicos desde el DTO
        existente.setNombre(dto.getNombre());
        existente.setCorreo(dto.getCorreo());
        existente.setTelefono(dto.getTelefono());
        existente.setCedula(dto.getCedula());

        existente = usuarioServicioRepository.save(existente);
        return modelMapper.map(existente, UsuarioServicioDTO.class);
    }

    // Eliminar (si lo expones)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuarioServicio(@PathVariable Long id) throws EntityNotFoundException {
        if (!usuarioServicioRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe UsuarioServicio con id " + id);
        }
        usuarioServicioRepository.deleteById(id);
    }
}

