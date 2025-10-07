package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.services.UsuarioConductorService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioConductorServiceTest {

    @Test
    void rf3RegistrarConductor_ok() {
        var repo = Mockito.mock(UsuarioConductorRepository.class);
        var cRepo = Mockito.mock(CiudadRepository.class);
        var svc = new UsuarioConductorService(repo, cRepo);

        var ciudad = new CiudadEntity(); ciudad.setId(1L);
        Mockito.when(cRepo.findById(1L)).thenReturn(Optional.of(ciudad));
        Mockito.when(repo.save(Mockito.any(UsuarioConductorEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf3RegistrarConductor("Luis","l@l.co","300","222",1L,true);
        assertTrue(out.isDisponible());
        assertEquals(1L, out.getCiudad().getId());
    }
}
