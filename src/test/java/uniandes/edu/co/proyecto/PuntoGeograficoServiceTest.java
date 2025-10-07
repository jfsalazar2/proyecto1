package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.PuntoGeograficoRepository;
import uniandes.edu.co.proyecto.services.PuntoGeograficoService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PuntoGeograficoServiceTest {

    @Test
    void rf7RegistrarPunto_ok() {
        var repo = Mockito.mock(PuntoGeograficoRepository.class);
        var cRepo = Mockito.mock(CiudadRepository.class);
        var svc = new PuntoGeograficoService(repo, cRepo);

        var ciudad = new CiudadEntity(); ciudad.setId(1L);
        Mockito.when(cRepo.findById(1L)).thenReturn(Optional.of(ciudad));
        Mockito.when(repo.save(Mockito.any(PuntoGeograficoEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf7RegistrarPunto("Uniandes","Calle 1","4.60,-74.06",1L);
        assertEquals("Uniandes", out.getNombre());
        assertEquals(1L, out.getCiudad().getId());
    }
}
