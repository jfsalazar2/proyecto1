package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;
import uniandes.edu.co.proyecto.services.UsuarioServicioService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServicioServiceTest {

    @Test
    void rf2RegistrarUsuarioServicio_ok() {
        var repo = Mockito.mock(UsuarioServicioRepository.class);
        var ciudadRepo = Mockito.mock(CiudadRepository.class);
        var svc = new UsuarioServicioService(repo, ciudadRepo);

        var ciudad = new CiudadEntity(); ciudad.setId(1L); ciudad.setNombre("Bogotá");
        Mockito.when(ciudadRepo.findById(1L)).thenReturn(Optional.of(ciudad));
        Mockito.when(repo.save(Mockito.any(UsuarioServicioEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf2RegistrarUsuarioServicio("Ana","a@a.co","301","111",1L);
        assertEquals("Ana", out.getNombre());
        assertEquals("Bogotá", out.getCiudad().getNombre());
    }
}
