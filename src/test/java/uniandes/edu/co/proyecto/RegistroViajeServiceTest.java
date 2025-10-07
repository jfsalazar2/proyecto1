package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.services.RegistroViajeService;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RegistroViajeServiceTest {

    @Test
    void rf9RegistrarViaje_ok() {
        var sRepo = Mockito.mock(ServicioRepository.class);
        var cRepo = Mockito.mock(UsuarioConductorRepository.class);
        var svc = new RegistroViajeService(sRepo, cRepo);

        var serv = new ServicioEntity(){};
        serv.setId(9L);
        var conductor = new UsuarioConductorEntity(); conductor.setId(8L);
        serv.setConductor(conductor);

        Mockito.when(sRepo.findById(9L)).thenReturn(Optional.of(serv));
        Mockito.when(sRepo.save(Mockito.any(ServicioEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf9RegistrarViaje(9L, LocalTime.of(10,0), LocalTime.of(10,30), 8000, 30);
        assertTrue(out.getEstado());
        assertEquals(8000, out.getCosto());
        assertEquals(30, out.getDuracion());
        assertEquals(LocalTime.of(10,30), out.getHoraFin());
    }
}
