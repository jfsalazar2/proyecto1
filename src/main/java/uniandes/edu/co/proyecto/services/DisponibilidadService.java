package uniandes.edu.co.proyecto.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository; 
import uniandes.edu.co.proyecto.util.HorarioParser;
import uniandes.edu.co.proyecto.util.TimeRange;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;

@Service
public class DisponibilidadService {

    private static final Set<String> DIAS_VALIDOS = Set.of(
            "LUNES","MARTES","MIERCOLES","MIÉRCOLES","JUEVES","VIERNES","SABADO","SÁBADO","DOMINGO"
    );

    private final DisponibilidadRepository repo;
    private final UsuarioConductorRepository conductorRepo;
    private final VehiculoRepository vehiculoRepo;

    public DisponibilidadService(DisponibilidadRepository repo,
                                 UsuarioConductorRepository conductorRepo,
                                 VehiculoRepository vehiculoRepo) {
        this.repo = repo;
        this.conductorRepo = conductorRepo;
        this.vehiculoRepo = vehiculoRepo;
    }

    // ===== RF5 =====
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public DisponibilidadEntity rf5RegistrarDisponibilidad(Long conductorId, Long vehiculoId,
                                                           String tipoServicio, List<String> dias, String horario) {
        UsuarioConductorEntity conductor = conductorRepo.findById(conductorId)
                .orElseThrow(() -> new NotFoundException("Conductor no encontrado"));
        VehiculoEntity vehiculo = vehiculoRepo.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("Vehículo no encontrado"));

        
        validarDatosBasicos(tipoServicio, dias, horario);
        List<String> diasNorm = normalizarDias(dias);
        TimeRange nuevo = parseRango(horario);

        
        if (vehiculo.getPropietario() == null || !Objects.equals(vehiculo.getPropietario().getId(), conductor.getId())) {
            throw new BusinessException("El vehículo no pertenece al conductor");
        }
        
        if (vehiculo.getCiudad() != null && conductor.getCiudad() != null
                && !Objects.equals(vehiculo.getCiudad().getId(), conductor.getCiudad().getId())) {
            throw new BusinessException("Conductor y vehículo deben estar en la misma ciudad");
        }
        
        if (!booleanFlagOrDefault(conductor, "isDisponible", true)) {
            throw new BusinessException("El conductor no está disponible");
        }
        if (!booleanFlagOrDefault(vehiculo, "isActivo", true)) {
            throw new BusinessException("El vehículo no está activo");
        }

        
        List<DisponibilidadEntity> existentes = repo.findByConductor(conductor);
        verificarTraslapes(existentes, diasNorm, nuevo, null);

        try {
            DisponibilidadEntity disp = new DisponibilidadEntity();
            disp.setConductor(conductor);
            disp.setVehiculo(vehiculo);
            disp.setTipoServicio(tipoServicio.trim());
            disp.setDias(new ArrayList<>(diasNorm));
            
            disp.setHorario(normalizarHorario(horario));
            return repo.save(disp);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException("Ya existe una disponibilidad igual o en conflicto para este conductor");
        }
    }

    // ===== RF6 =====
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public DisponibilidadEntity rf6ModificarDisponibilidad(Long disponibilidadId,
                                                           List<String> nuevosDias, String nuevoHorario) {
        DisponibilidadEntity d = repo.findById(disponibilidadId)
                .orElseThrow(() -> new NotFoundException("Disponibilidad no encontrada"));

        validarDatosBasicos(d.getTipoServicio(), nuevosDias, nuevoHorario);
        List<String> diasNorm = normalizarDias(nuevosDias);
        TimeRange nuevo = parseRango(nuevoHorario);

        // Si tu entidad tiene flag enUso, lo validamos por reflexión sin romper compilación:
        if (booleanFlagOrDefault(d, "isEnUso", false)) {
            throw new BusinessException("No se puede modificar una disponibilidad en uso");
        }

        List<DisponibilidadEntity> existentes = repo.findByConductor(d.getConductor());
        verificarTraslapes(existentes, diasNorm, nuevo, d.getId());

        d.setDias(new ArrayList<>(diasNorm));
        d.setHorario(normalizarHorario(nuevoHorario));
        return repo.save(d);
    }

    

    private void validarDatosBasicos(String tipoServicio, List<String> dias, String horario) {
        if (isBlank(tipoServicio)) throw new BusinessException("El tipo de servicio es obligatorio");
        if (dias == null || dias.isEmpty()) throw new BusinessException("Debe registrar al menos un día");
        if (isBlank(horario)) throw new BusinessException("El horario es obligatorio");
        if (!HorarioParser.isValid(horario)) {
            throw new BusinessException("Horario inválido. Formato esperado HH:mm-HH:mm");
        }
        
        parseRango(horario); 
    }

    private TimeRange parseRango(String horario) {
        return HorarioParser.parse(horario)
                .orElseThrow(() -> new BusinessException("No fue posible interpretar el rango horario"));
    }

    private String normalizarHorario(String horario) {
        return horario == null ? "" : horario.trim();
    }

    private List<String> normalizarDias(List<String> dias) {
        return dias.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toUpperCase)
                .map(s -> s.replace("É", "E"))                
                .map(s -> s.equals("MIÉRCOLES") ? "MIERCOLES" : s) 
                .filter(DIAS_VALIDOS::contains)
                .distinct()
                .sorted(Comparator.comparingInt(this::ordenDia)) // <-- CORREGIDO
                .collect(Collectors.toList());
    }

    
    private int ordenDia(String d) {
        switch (d) {
            case "LUNES": return 1;
            case "MARTES": return 2;
            case "MIERCOLES":
            case "MIÉRCOLES": return 3;
            case "JUEVES": return 4;
            case "VIERNES": return 5;
            case "SABADO":
            case "SÁBADO": return 6;
            case "DOMINGO": return 7;
            default: return 99;
        }
    }

    private void verificarTraslapes(List<DisponibilidadEntity> existentes,
                                    List<String> diasPropuestos,
                                    TimeRange nuevo,
                                    Long ignorarId) {
        for (DisponibilidadEntity e : existentes) {
            if (ignorarId != null && Objects.equals(e.getId(), ignorarId)) continue;

            List<String> inter = interseccionDias(e.getDias(), diasPropuestos);
            if (inter.isEmpty()) continue;

            TimeRange actual = HorarioParser.parse(e.getHorario()).orElse(null);
            if (actual == null) continue;

            
            if (actual.overlaps(nuevo)) {
                throw new BusinessException(
                        "La disponibilidad propuesta se superpone con id=" + e.getId()
                                + " en días: " + String.join(",", inter));
            }
        }
    }

    private List<String> interseccionDias(List<String> a, List<String> b) {
        if (a == null || b == null) return List.of();
        Set<String> sa = a.stream().map(s -> s.toUpperCase().replace("É","E")).collect(Collectors.toSet());
        Set<String> sb = b.stream().map(s -> s.toUpperCase().replace("É","E")).collect(Collectors.toSet());
        sa.retainAll(sb);
        return new ArrayList<>(sa);
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    
    private boolean booleanFlagOrDefault(Object target, String methodName, boolean defaultVal) {
        try {
            var m = target.getClass().getMethod(methodName);
            Object r = m.invoke(target);
            if (r instanceof Boolean) return (Boolean) r;
        } catch (Exception ignore) { /* no-op */ }
        return defaultVal;
    }
}


