package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CiudadService {
    private final CiudadRepository ciudadRepo;
    public CiudadService(CiudadRepository ciudadRepo) { this.ciudadRepo = ciudadRepo; }

    @Transactional
    public CiudadEntity rf1RegistrarCiudad(String nombre) {
        if (ciudadRepo.existsByNombre(nombre))
            throw new BusinessException("La ciudad ya existe: " + nombre);
        CiudadEntity c = new CiudadEntity();
        c.setNombre(nombre);
        return ciudadRepo.save(c);
    }
}
