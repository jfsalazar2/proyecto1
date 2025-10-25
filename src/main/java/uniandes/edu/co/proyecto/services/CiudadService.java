package uniandes.edu.co.proyecto.services;

import javax.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;

@Service
public class CiudadService {

    private final CiudadRepository ciudadRepo;

    public CiudadService(CiudadRepository ciudadRepo) {
        this.ciudadRepo = ciudadRepo;
    }

    
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public CiudadEntity rf1RegistrarCiudad(@NotBlank String nombre) {
        String normalizado = normalizarNombre(nombre);

        if (normalizado.isBlank()) {
            throw new BusinessException("El nombre de la ciudad no puede ser vacío");
        }

        
        boolean existe = ciudadRepo.existsByNombreIgnoreCase(normalizado);
        if (existe) {
            throw new BusinessException("La ciudad ya existe: " + normalizado);
        }

        CiudadEntity c = new CiudadEntity();
        c.setNombre(normalizado);
        return ciudadRepo.save(c);
    }

    private String normalizarNombre(String raw) {
        if (raw == null) return "";
        
        String colapsado = raw.trim().replaceAll("\\s+", " ");
        
        String[] partes = colapsado.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : partes) {
            if (p.isBlank()) continue;
            sb.append(Character.toUpperCase(p.charAt(0)))
              .append(p.length() > 1 ? p.substring(1) : "")
              .append(" ");
        }
        return sb.toString().trim();
    }
}

