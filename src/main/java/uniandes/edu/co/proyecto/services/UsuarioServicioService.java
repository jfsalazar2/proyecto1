package uniandes.edu.co.proyecto.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;

@Service
public class UsuarioServicioService {

    private final UsuarioServicioRepository repo;
    private final CiudadRepository ciudadRepo;

    public UsuarioServicioService(UsuarioServicioRepository repo, CiudadRepository ciudadRepo) {
        this.repo = repo;
        this.ciudadRepo = ciudadRepo;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public UsuarioServicioEntity rf2RegistrarUsuarioServicio(
            String nombre, String correo, String telefono, String cedula, Long ciudadId) {

        if (isBlank(nombre))  throw new BusinessException("El nombre es obligatorio");
        if (isBlank(correo))  throw new BusinessException("El correo es obligatorio");
        if (isBlank(telefono)) throw new BusinessException("El teléfono es obligatorio");
        if (isBlank(cedula))  throw new BusinessException("La cédula es obligatoria");
        if (ciudadId == null) throw new BusinessException("La ciudad es obligatoria");

        String correoNorm = normalizarCorreo(correo);
        if (!esCorreoValido(correoNorm)) {
            throw new BusinessException("El correo no tiene un formato válido");
        }
        String telNorm = normalizarTelefono(telefono);
        if (telNorm.length() < 7) {
            throw new BusinessException("El teléfono es demasiado corto");
        }
        String cedulaNorm = cedula.trim();

        CiudadEntity ciudad = ciudadRepo.findById(ciudadId)
                .orElseThrow(() -> new NotFoundException("Ciudad no encontrada"));

        if (repo.existsByCorreoIgnoreCase(correoNorm)) {
            throw new BusinessException("Ya existe un usuario con ese correo");
        }
        if (repo.existsByCedula(cedulaNorm)) {
            throw new BusinessException("Ya existe un usuario con esa cédula");
        }

        try {
            UsuarioServicioEntity u = new UsuarioServicioEntity();
            u.setNombre(nombre.trim());
            u.setCorreo(correoNorm);
            u.setTelefono(telNorm);
            u.setCedula(cedulaNorm);
            u.setCiudad(ciudad);
            return repo.save(u);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("No se pudo registrar: correo o cédula ya existen");
        }
    }

    
    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private String normalizarCorreo(String correo) {
        return correo == null ? "" : correo.trim().toLowerCase();
    }


    private boolean esCorreoValido(String correo) {
        return correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    
    private String normalizarTelefono(String tel) {
        if (tel == null) return "";
        String t = tel.trim();
        if (t.startsWith("+")) {
            return "+" + t.replaceAll("[^0-9]", "");
        }
        return t.replaceAll("[^0-9]", "");
    }
}
