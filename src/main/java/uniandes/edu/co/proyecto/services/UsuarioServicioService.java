package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicioService {
    private final UsuarioServicioRepository repo;
    private final CiudadRepository ciudadRepo;

    public UsuarioServicioService(UsuarioServicioRepository repo, CiudadRepository ciudadRepo) {
        this.repo = repo; this.ciudadRepo = ciudadRepo;
    }

    @Transactional
    public UsuarioServicioEntity rf2RegistrarUsuarioServicio(String nombre, String correo, String telefono,
                                                             String cedula, Long ciudadId) {
        CiudadEntity ciudad = ciudadRepo.findById(ciudadId)
                .orElseThrow(() -> new NotFoundException("Ciudad no encontrada"));
        UsuarioServicioEntity u = new UsuarioServicioEntity();
        u.setNombre(nombre); u.setCorreo(correo); u.setTelefono(telefono);
        u.setCedula(cedula); u.setCiudad(ciudad);
        return repo.save(u);
    }
}