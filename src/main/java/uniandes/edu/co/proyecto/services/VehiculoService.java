package uniandes.edu.co.proyecto.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.entities.enums.TipoVehiculo;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;

@Service
public class VehiculoService {

    private final VehiculoRepository repo;
    private final UsuarioConductorRepository conductorRepo;
    private final CiudadRepository ciudadRepo;

    public VehiculoService(VehiculoRepository repo,
                           UsuarioConductorRepository conductorRepo,
                           CiudadRepository ciudadRepo) {
        this.repo = repo;
        this.conductorRepo = conductorRepo;
        this.ciudadRepo = ciudadRepo; 
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public VehiculoEntity rf4RegistrarVehiculo(Long propietarioId,
                                               Long ciudadId,
                                               String placa,
                                               TipoVehiculo tipo,
                                               String marca,
                                               String modelo,
                                               String color,
                                               String ciudadExpPlaca,
                                               Integer capacidad) {

        // Requeridos
        if (propietarioId == null) throw new BusinessException("El propietario es obligatorio");
        if (ciudadId == null)      throw new BusinessException("La ciudad es obligatoria");
        if (isBlank(placa))         throw new BusinessException("La placa es obligatoria");
        if (tipo == null)           throw new BusinessException("El tipo de vehículo es obligatorio");
        if (isBlank(marca))         throw new BusinessException("La marca es obligatoria");
        if (isBlank(modelo))        throw new BusinessException("El modelo es obligatorio");
        if (isBlank(color))         throw new BusinessException("El color es obligatorio");
        if (capacidad == null || capacidad <= 0) {
            throw new BusinessException("La capacidad debe ser un entero positivo");
        }

    
        UsuarioConductorEntity propietario = conductorRepo.findById(propietarioId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));
        CiudadEntity ciudad = ciudadRepo.findById(ciudadId)
                .orElseThrow(() -> new NotFoundException("Ciudad no encontrada"));

        
        if (propietario.getCiudad() != null && !propietario.getCiudad().getId().equals(ciudad.getId())) {
            throw new BusinessException("El vehículo debe registrarse en la misma ciudad del conductor");
        }

        
        String placaNorm = normalizarPlaca(placa);
        String marcaNorm = titulo(marca);
        String modeloNorm = modelo.trim();
        String colorNorm  = titulo(color);
        String ciudadExpNorm = isBlank(ciudadExpPlaca) ? null : titulo(ciudadExpPlaca);

        
        if (repo.existsByPlacaIgnoreCase(placaNorm)) {
            throw new BusinessException("Ya existe un vehículo con la placa " + placaNorm);
        }

        
        if (repo.existsByPropietarioIdAndActivoTrue(propietarioId)) {
            throw new BusinessException("El conductor ya tiene un vehículo activo registrado");
        }

        try {
            VehiculoEntity v = new VehiculoEntity();
            v.setPropietario(propietario);
            v.setCiudad(ciudad);
            v.setPlaca(placaNorm);
            v.setTipo(tipo);
            v.setMarca(marcaNorm);
            v.setModelo(modeloNorm);
            v.setColor(colorNorm);
            v.setCiudadExpPlaca(ciudadExpNorm);
            v.setCapacidad(capacidad);
            // si tu entidad tiene estado/activo:
            // v.setActivo(true);
            return repo.save(v);
        } catch (DataIntegrityViolationException e) {
            // Respaldo por constraint de unicidad en DB
            throw new BusinessException("No se pudo registrar: la placa ya existe o hay conflicto de unicidad");
        }
    }

    public VehiculoEntity findByIdOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Vehículo no encontrado"));
    }

    
    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    
    private String normalizarPlaca(String placa) {
        String p = placa == null ? "" : placa.trim().toUpperCase();
        
        return p.replaceAll("[\\s-]", "");
    }

    
    private String titulo(String raw) {
        if (isBlank(raw)) return "";
        String[] parts = raw.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String x : parts) {
            sb.append(Character.toUpperCase(x.charAt(0)))
              .append(x.length() > 1 ? x.substring(1) : "")
              .append(" ");
        }
        return sb.toString().trim();
    }
}

