package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.services.DisponibilidadService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DisponibilidadServiceTest {

    @Test
    void rf5RegistrarDisponibilidad_sinSolape_ok() {
        var repo = Mockito.mock(DisponibilidadRepository.class);
        var cRepo = Mockito.mock(UsuarioConductorRepository.class);
        var vRepo = Mockito.mock(VehiculoRepository.class);
        var svc = new DisponibilidadService(repo, cRepo, vRepo);

        var conductor = new UsuarioConductorEntity(); conductor.setId(1L);
        var vehiculo = new VehiculoEntity(); vehiculo.setId(2L);
        Mockito.when(cRepo.findById(1L)).thenReturn(Optional.of(conductor));
        Mockito.when(vRepo.findById(2L)).thenReturn(Optional.of(vehiculo));
        Mockito.when(repo.findByConductor(conductor)).thenReturn(List.of());
        Mockito.when(repo.save(Mockito.any(DisponibilidadEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf5RegistrarDisponibilidad(1L,2L,"Transporte", List.of("LUN"), "08:00-10:00");
        assertEquals("Transporte", out.getTipoServicio());
    }

    @Test
    void rf5RegistrarDisponibilidad_conSolape_falla() {
        var repo = Mockito.mock(DisponibilidadRepository.class);
        var cRepo = Mockito.mock(UsuarioConductorRepository.class);
        var vRepo = Mockito.mock(VehiculoRepository.class);
        var svc = new DisponibilidadService(repo, cRepo, vRepo);

        var conductor = new UsuarioConductorEntity(); conductor.setId(1L);
        var vehiculo = new VehiculoEntity(); vehiculo.setId(2L);
        var existente = new DisponibilidadEntity();
        existente.setId(9L);
        existente.setDias(List.of("LUN"));
        existente.setHorario("09:00-11:00");

        Mockito.when(cRepo.findById(1L)).thenReturn(Optional.of(conductor));
        Mockito.when(vRepo.findById(2L)).thenReturn(Optional.of(vehiculo));
        Mockito.when(repo.findByConductor(conductor)).thenReturn(List.of(existente));

        assertThrows(BusinessException.class, () ->
                svc.rf5RegistrarDisponibilidad(1L,2L,"Transporte", List.of("LUN"), "10:00-10:30"));
    }
}
