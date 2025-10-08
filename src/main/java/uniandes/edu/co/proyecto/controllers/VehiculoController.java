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
import uniandes.edu.co.proyecto.dtos.VehiculoDTO;

import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.entities.enums.TipoVehiculo;
import uniandes.edu.co.proyecto.exceptions.IllegalOperationException;

import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.services.VehiculoService;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService; 

    @Autowired
    private VehiculoRepository vehiculoRepository; 

    
    @Autowired
    private ModelMapper modelMapper;

    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDTO> getVehiculos() {
        List<VehiculoEntity> entidades = vehiculoRepository.findAll();
        java.lang.reflect.Type listType = new TypeToken<List<VehiculoDTO>>(){}.getType();
        return modelMapper.map(entidades, listType);
    }

    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehiculoDTO getVehiculo(@PathVariable Long id) throws EntityNotFoundException {
        VehiculoEntity e = vehiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe Vehiculo con id " + id));
        return modelMapper.map(e, VehiculoDTO.class);
    }

    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculoDTO createVehiculo(@RequestBody VehiculoDTO dto,
                                      @RequestParam("propietarioId") Long propietarioId,
                                      @RequestParam("ciudadId") Long ciudadId,
                                      @RequestParam("tipo") String tipoStr)
            throws IllegalOperationException, EntityNotFoundException {

        TipoVehiculo tipo = TipoVehiculo.valueOf(tipoStr); 

        
        VehiculoEntity in = modelMapper.map(dto, VehiculoEntity.class);

        VehiculoEntity creado = vehiculoService.rf4RegistrarVehiculo(
                propietarioId,
                ciudadId,
                in.getPlaca(),
                tipo,
                in.getMarca(),
                in.getModelo(),
                in.getColor(),
                in.getCiudadExpPlaca(),
                in.getCapacidad()
        );

        return modelMapper.map(creado, VehiculoDTO.class);
    }

    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehiculoDTO updateVehiculo(@PathVariable Long id, @RequestBody VehiculoDTO dto)
            throws EntityNotFoundException {

        VehiculoEntity existente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe Vehiculo con id " + id));

        VehiculoEntity in = modelMapper.map(dto, VehiculoEntity.class);

        if (in.getPlaca() != null) existente.setPlaca(in.getPlaca());
        if (in.getMarca() != null) existente.setMarca(in.getMarca());
        if (in.getModelo() != null) existente.setModelo(in.getModelo());
        if (in.getColor() != null) existente.setColor(in.getColor());
        if (in.getCiudadExpPlaca() != null) existente.setCiudadExpPlaca(in.getCiudadExpPlaca());
        if (in.getCapacidad() != null) existente.setCapacidad(in.getCapacidad());

        

        existente = vehiculoRepository.save(existente);
        return modelMapper.map(existente, VehiculoDTO.class);
    }

   
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehiculo(@PathVariable Long id) throws EntityNotFoundException {
        if (!vehiculoRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe Vehiculo con id " + id);
        }
        vehiculoRepository.deleteById(id);
    }
}