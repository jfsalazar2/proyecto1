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
        CiudadRepository repo = Mockito.mock(CiudadRepository.class);
        CiudadService svc = new CiudadService(repo); // 👈 tipo explícito

        Mockito.when(repo.existsByNombre("Bogotá")).thenReturn(false);
        Mockito.when(repo.save(Mockito.any(CiudadEntity.class)))
               .thenAnswer(inv -> inv.getArgument(0));

        CiudadEntity out = svc.rf1RegistrarCiudad("Bogotá"); // 👈 método existe
        assertEquals("Bogotá", out.getNombre());
        Mockito.verify(repo).save(Mockito.any(CiudadEntity.class));
    }

    @Test
    void rf1RegistrarCiudad_duplicada() {
        CiudadRepository repo = Mockito.mock(CiudadRepository.class);
        CiudadService svc = new CiudadService(repo);

        Mockito.when(repo.existsByNombre("Bogotá")).thenReturn(true);

        assertThrows(BusinessException.class, () -> svc.rf1RegistrarCiudad("Bogotá"));
    }
}
