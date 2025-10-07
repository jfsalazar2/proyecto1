package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.entities.enums.TipoVehiculo;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoService {
    private final VehiculoRepository repo;
    private final UsuarioConductorRepository conductorRepo;
    private final CiudadRepository ciudadRepo;

    public VehiculoService(VehiculoRepository repo, UsuarioConductorRepository conductorRepo, CiudadRepository ciudadRepo) {
        this.repo = repo; this.conductorRepo = conductorRepo; this.ciudadRepo = ciudadRepo;
    }

    @Transactional
    public VehiculoEntity rf4RegistrarVehiculo(Long propietarioId, Long ciudadId, String placa,
                                               TipoVehiculo tipo, String marca, String modelo,
                                               String color, String ciudadExpPlaca, Integer capacidad) {
        UsuarioConductorEntity propietario = conductorRepo.findById(propietarioId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));
        CiudadEntity ciudad = ciudadRepo.findById(ciudadId)
                .orElseThrow(() -> new NotFoundException("Ciudad no encontrada"));

        VehiculoEntity v = new VehiculoEntity();
        v.setPropietario(propietario);
        v.setCiudad(ciudad);
        v.setPlaca(placa);
        v.setTipo(tipo);
        v.setMarca(marca);
        v.setModelo(modelo);
        v.setColor(color);
        v.setCiudadExpPlaca(ciudadExpPlaca);
        v.setCapacidad(capacidad);
        return repo.save(v);
    }
}
