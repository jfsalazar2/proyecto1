package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioConductorService {
    private final UsuarioConductorRepository repo;
    private final CiudadRepository ciudadRepo;

    public UsuarioConductorService(UsuarioConductorRepository repo, CiudadRepository ciudadRepo) {
        this.repo = repo; this.ciudadRepo = ciudadRepo;
    }

    @Transactional
    public UsuarioConductorEntity rf3RegistrarConductor(String nombre, String correo, String telefono,
                                                        String cedula, Long ciudadId, boolean disponibleInicial) {
        CiudadEntity ciudad = ciudadRepo.findById(ciudadId)
                .orElseThrow(() -> new NotFoundException("Ciudad no encontrada"));
        UsuarioConductorEntity c = new UsuarioConductorEntity();
        c.setNombre(nombre); c.setCorreo(correo); c.setTelefono(telefono);
        c.setCedula(cedula); c.setCiudad(ciudad); c.setDisponible(disponibleInicial);
        return repo.save(c);
    }
}

