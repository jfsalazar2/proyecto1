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
import uniandes.edu.co.proyecto.dtos.UsuarioConductorDTO;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.exceptions.IllegalOperationException;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.services.UsuarioConductorService;

@RestController
@RequestMapping("/usuarios/conductor")
public class UsuarioConductorController {

    @Autowired
    private UsuarioConductorService usuarioConductorService;

    @Autowired
    private UsuarioConductorRepository usuarioConductorRepository; // usamos repo para listados/gets

    @Autowired
    private ModelMapper modelMapper;

    // LISTAR usando repo (evita getConductores() en el service)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioConductorDTO> getConductores() {
        List<UsuarioConductorEntity> entidades = usuarioConductorRepository.findAll();
        java.lang.reflect.Type listType = new TypeToken<List<UsuarioConductorDTO>>(){}.getType();
        return modelMapper.map(entidades, listType);
    }

    // GET por id usando repo
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioConductorDTO getConductor(@PathVariable Long id) throws EntityNotFoundException {
        UsuarioConductorEntity e = usuarioConductorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe UsuarioConductor con id " + id));
        return modelMapper.map(e, UsuarioConductorDTO.class);
    }

    // RF3 - Registrar Conductor
    // Como tu DTO no tiene ciudadId() ni disponible(), los recibimos como query params:
    // POST /usuarios/conductor?ciudadId=1&disponibleInicial=true
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioConductorDTO crearConductor(@RequestBody UsuarioConductorDTO dto,
                                              @RequestParam("ciudadId") Long ciudadId,
                                              @RequestParam(value = "disponibleInicial", defaultValue = "true") boolean disponibleInicial)
            throws EntityNotFoundException, IllegalOperationException {

        var creado = usuarioConductorService.rf3RegistrarConductor(
                dto.getNombre(), dto.getCorreo(), dto.getTelefono(), dto.getCedula(), ciudadId, disponibleInicial
        );
        return modelMapper.map(creado, UsuarioConductorDTO.class);
    }

    // PUT básico (si quieres exponerlo). Usa repo si tu service no tiene update.
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioConductorDTO updateConductor(@PathVariable Long id,
                                               @RequestBody UsuarioConductorDTO dto)
            throws EntityNotFoundException {
        UsuarioConductorEntity existente = usuarioConductorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe UsuarioConductor con id " + id));

        existente.setNombre(dto.nombre());
        existente.setCorreo(dto.getCorreo());
        existente.setTelefono(dto.getTelefono());
        existente.setCedula(dto.getCedula());
        // disponibleInicial se maneja en RF3 (alta); aquí podrías exponer otro endpoint específico si hace falta

        existente = usuarioConductorRepository.save(existente);
        return modelMapper.map(existente, UsuarioConductorDTO.class);
    }

    // DELETE básico
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConductor(@PathVariable Long id) throws EntityNotFoundException {
        if (!usuarioConductorRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe UsuarioConductor con id " + id);
        }
        usuarioConductorRepository.deleteById(id);
    }
}