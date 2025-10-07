package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.exceptions.BusinessException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.services.CiudadService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CiudadServiceTest {

    @Test
    void rf1RegistrarCiudad_ok() {
        var repo = Mockito.mock(CiudadRepository.class);
        var svc = new CiudadService(repo);

        Mockito.when(repo.existsByNombre("Bogotá")).thenReturn(false);
        Mockito.when(repo.save(Mockito.any(CiudadEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = ((Object) svc).rf1RegistrarCiudad("Bogotá");
        assertEquals("Bogotá", ((CiudadEntity) out).getNombre());
        Mockito.verify(repo).save(Mockito.any(CiudadEntity.class));
    }

    @Test
    void rf1RegistrarCiudad_duplicada() {
        var repo = Mockito.mock(CiudadRepository.class);
        var svc = new CiudadService(repo);
        Mockito.when(repo.existsByNombre("Bogotá")).thenReturn(true);
        assertThrows(BusinessException.class, () -> svc.rf1RegistrarCiudad("Bogotá"));
    }
}
