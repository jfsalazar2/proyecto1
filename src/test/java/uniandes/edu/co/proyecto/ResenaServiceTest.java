package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.repositories.*;
import uniandes.edu.co.proyecto.services.ResenaService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ResenaServiceTest {

    @Test
    void rf10ClienteAConductor_ok() {
        var repo = Mockito.mock(ResenaRepository.class);
        var sRepo = Mockito.mock(ServicioRepository.class);
        var cRepo = Mockito.mock(UsuarioConductorRepository.class);
        var uRepo = Mockito.mock(UsuarioServicioRepository.class);
        var svc = new ResenaService(repo, sRepo, cRepo, uRepo);

        var serv = new ServicioEntity(){};
        var autor = new UsuarioServicioEntity();
        var conductor = new UsuarioConductorEntity();

        Mockito.when(sRepo.findById(1L)).thenReturn(Optional.of(serv));
        Mockito.when(uRepo.findById(2L)).thenReturn(Optional.of(autor));
        Mockito.when(cRepo.findById(3L)).thenReturn(Optional.of(conductor));
        Mockito.when(repo.save(Mockito.any(ResenaEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf10ClienteAConductor(1L,2L,3L,5,"Excelente");
        assertEquals(5, out.getCalificacion());
        assertEquals("Excelente", out.getComentario());
        assertEquals(conductor, out.getConductorResenado());
    }

    @Test
    void rf11ConductorACliente_ok() {
        var repo = Mockito.mock(ResenaRepository.class);
        var sRepo = Mockito.mock(ServicioRepository.class);
        var cRepo = Mockito.mock(UsuarioConductorRepository.class);
        var uRepo = Mockito.mock(UsuarioServicioRepository.class);
        var svc = new ResenaService(repo, sRepo, cRepo, uRepo);

        Mockito.when(sRepo.findById(1L)).thenReturn(Optional.of(new ServicioEntity(){}));
        Mockito.when(cRepo.findById(10L)).thenReturn(Optional.of(new UsuarioConductorEntity()));
        Mockito.when(uRepo.findById(20L)).thenReturn(Optional.of(new UsuarioServicioEntity()));
        Mockito.when(repo.save(Mockito.any(ResenaEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf11ConductorACliente(1L,10L,20L,4,"Buen pasajero");
        assertEquals(4, out.getCalificacion());
        assertEquals("Buen pasajero", out.getComentario());
    }
}
