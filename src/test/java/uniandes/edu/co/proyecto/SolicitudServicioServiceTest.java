package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.entities.enums.NivelVehiculo;
import uniandes.edu.co.proyecto.repositories.*;
import uniandes.edu.co.proyecto.services.SolicitudServicioService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudServicioServiceTest {

    @Test
    void rf8SolicitarServicio_asignaCandidato_ok() {
        var servRepo = Mockito.mock(ServicioRepository.class);
        var cliRepo = Mockito.mock(UsuarioServicioRepository.class);
        var puntoRepo = Mockito.mock(PuntoGeograficoRepository.class);
        var condRepo = Mockito.mock(UsuarioConductorRepository.class);
        var vehRepo = Mockito.mock(VehiculoRepository.class);
        var dispRepo = Mockito.mock(DisponibilidadRepository.class);

        var svc = new SolicitudServicioService(servRepo, cliRepo, puntoRepo, condRepo, vehRepo, dispRepo);

        var cliente = new UsuarioServicioEntity(); cliente.setId(1L);
        var ciudad = new CiudadEntity(); ciudad.setId(10L);
        var origen = new PuntoGeograficoEntity(); origen.setId(2L); origen.setCiudad(ciudad); origen.setCoordenadas("4.6,-74.06");
        var destino= new PuntoGeograficoEntity(); destino.setId(3L); destino.setCiudad(ciudad); destino.setCoordenadas("4.61,-74.07");

        var conductor = new UsuarioConductorEntity(); conductor.setId(5L);
        var vehiculo = new VehiculoEntity(); vehiculo.setId(6L); vehiculo.setCiudad(ciudad);

        var disp = new DisponibilidadEntity();
        disp.setConductor(conductor); disp.setVehiculo(vehiculo); disp.setTipoServicio("Transporte");
        Mockito.when(cliRepo.findById(1L)).thenReturn(Optional.of(cliente));
        Mockito.when(puntoRepo.findById(2L)).thenReturn(Optional.of(origen));
        Mockito.when(puntoRepo.findById(3L)).thenReturn(Optional.of(destino));
        Mockito.when(dispRepo.findByTipoServicio("Transporte")).thenReturn(List.of(disp));

        ArgumentCaptor<ServicioEntity> cap = ArgumentCaptor.forClass(ServicioEntity.class);
        Mockito.when(servRepo.save(cap.capture())).thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf8SolicitarServicio(1L, "Transporte", 10L, 2L, 3L,
                1000, 5, LocalDate.now(), LocalTime.NOON, NivelVehiculo.ESTANDAR);

        assertNotNull(out.getVehiculo());
        assertEquals(6L, out.getVehiculo().getId());
        assertEquals("Transporte", out.getTipo());
    }
}