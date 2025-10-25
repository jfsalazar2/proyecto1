package uniandes.edu.co.proyecto.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.PuntoGeograficoRepository;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

@Service
public class PuntoGeograficoService {

    private final PuntoGeograficoRepository repo;
    private final CiudadRepository ciudadRepo;

    public PuntoGeograficoService(PuntoGeograficoRepository repo, CiudadRepository ciudadRepo) {
        this.repo = repo;
        this.ciudadRepo = ciudadRepo;
    }

    
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)

    public PuntoGeograficoEntity rf7RegistrarPunto(String nombre,
                                                   String direccion,
                                                   String coordenadas,
                                                   Long ciudadId) {
        if (isBlank(nombre))    throw new BusinessException("El nombre es obligatorio");
        if (isBlank(direccion)) throw new BusinessException("La dirección es obligatoria");
        if (isBlank(coordenadas)) throw new BusinessException("Las coordenadas son obligatorias");
        if (ciudadId == null)   throw new BusinessException("La ciudad es obligatoria");

        CiudadEntity ciudad = ciudadRepo.findById(ciudadId)
                .orElseThrow(() -> new NotFoundException("Ciudad no encontrada"));

        String nomNorm = titulo(nombre);
        String dirNorm = normalizarDireccion(direccion);
        Coord coord = parseCoord(coordenadas); 

        
        if (repo.existsByCiudadAndNombreIgnoreCase(ciudad, nomNorm)) {
            throw new BusinessException("Ya existe un punto con ese nombre en la ciudad");
        }
        if (repo.existsByCiudadAndDireccionIgnoreCase(ciudad, dirNorm)) {
            throw new BusinessException("Ya existe un punto con esa dirección en la ciudad");
        }
        
        if (repo.existsByCiudadIdAndLatAndLng(ciudad.getId(), coord.lat, coord.lng)) {
            throw new BusinessException("Ya existe un punto con esas coordenadas en la ciudad");
        }
        

        try {
            PuntoGeograficoEntity p = new PuntoGeograficoEntity();
            p.setNombre(nomNorm);
            p.setDireccion(dirNorm);
            p.setCiudad(ciudad);

            
            try {
                p.getClass().getMethod("setLat", BigDecimal.class).invoke(p, coord.lat);
                p.getClass().getMethod("setLng", BigDecimal.class).invoke(p, coord.lng);
            } catch (ReflectiveOperationException ignore) {
                
                p.setCoordenadas(coord.asString());
            }

            return repo.save(p);
        } catch (DataIntegrityViolationException e) {
            
            throw new BusinessException("No se pudo registrar el punto: ya existe un punto igual en la ciudad");
        }
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private String titulo(String raw) {
        if (isBlank(raw)) return "";
        String[] parts = raw.trim().toLowerCase(Locale.ROOT).split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : parts) {
            sb.append(Character.toUpperCase(w.charAt(0)))
              .append(w.length() > 1 ? w.substring(1) : "")
              .append(" ");
        }
        return sb.toString().trim();
    }

    private String normalizarDireccion(String d) {
        if (d == null) return "";
        
        String s = d.trim().replaceAll("\\s+", " ");
        return s;
    }

    
    private Coord parseCoord(String raw) {
        String s = raw.trim().replace(";", ",").replaceAll("\\s+", "");
        String[] parts = s.split(",");
        if (parts.length != 2) throw new BusinessException("Coordenadas inválidas. Use formato 'lat,lon'");

        try {
            double lat = Double.parseDouble(parts[0]);
            double lon = Double.parseDouble(parts[1]);
            if (lat < -90 || lat > 90)   throw new BusinessException("Latitud fuera de rango [-90, 90]");
            if (lon < -180 || lon > 180) throw new BusinessException("Longitud fuera de rango [-180, 180]");
            
            BigDecimal blat = BigDecimal.valueOf(Math.round(lat * 1_000_000d) / 1_000_000d);
            BigDecimal blon = BigDecimal.valueOf(Math.round(lon * 1_000_000d) / 1_000_000d);
            return new Coord(blat, blon);
        } catch (NumberFormatException ex) {
            throw new BusinessException("Coordenadas inválidas. Use números en grados decimales, ej: 4.60971,-74.08175");
        }
    }

    private static final class Coord {
    final BigDecimal lat;
    final BigDecimal lng;
    Coord(BigDecimal lat, BigDecimal lng) { 
        this.lat = Objects.requireNonNull(lat); 
        this.lng = Objects.requireNonNull(lng); 
    }
    String asString() { 
        return lat.toPlainString() + "," + lng.toPlainString(); 
    }
}

}
