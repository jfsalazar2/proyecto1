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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import uniandes.edu.co.proyecto.dtos.CiudadDTO;


import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.services.CiudadService;
import uniandes.edu.co.proyecto.exceptions.EntityNotFoundException;
import uniandes.edu.co.proyecto.exceptions.IllegalOperationException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;


@RestController
@RequestMapping("/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private CiudadRepository ciudadRepository; 
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CiudadDTO> getCiudades() {
        List<CiudadEntity> entidades = ciudadRepository.findAll();
        java.lang.reflect.Type listType = new TypeToken<List<CiudadDTO>>(){}.getType();
        return modelMapper.map(entidades, listType);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CiudadDTO getCiudad(@PathVariable Long id) throws EntityNotFoundException {
        CiudadEntity e = ciudadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe Ciudad con id " + id));
        return modelMapper.map(e, CiudadDTO.class);
    }

    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CiudadDTO crearCiudad(@RequestBody CiudadDTO dto) throws IllegalOperationException {
        CiudadEntity creada = ciudadService.rf1RegistrarCiudad(dto.getNombre());
        return modelMapper.map(creada, CiudadDTO.class);
    }

    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CiudadDTO updateCiudad(@PathVariable Long id, @RequestBody CiudadDTO dto)
            throws EntityNotFoundException, IllegalOperationException {
        CiudadEntity existente = ciudadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe Ciudad con id " + id));
        existente.setNombre(dto.getNombre());
        existente = ciudadRepository.save(existente);
        return modelMapper.map(existente, CiudadDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCiudad(@PathVariable Long id) throws EntityNotFoundException {
        if (!ciudadRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe Ciudad con id " + id);
        }
        ciudadRepository.deleteById(id);
    }
}