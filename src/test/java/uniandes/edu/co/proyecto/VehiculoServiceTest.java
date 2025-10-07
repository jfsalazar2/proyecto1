package uniandes.edu.co.proyecto;
import uniandes.edu.co.proyecto.entities.*;
import uniandes.edu.co.proyecto.entities.enums.TipoVehiculo;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.services.VehiculoService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VehiculoServiceTest {

    @Test
    void rf4RegistrarVehiculo_ok() {
        var vRepo = Mockito.mock(VehiculoRepository.class);
        var uRepo = Mockito.mock(UsuarioConductorRepository.class);
        var cRepo = Mockito.mock(CiudadRepository.class);
        var svc = new VehiculoService(vRepo, uRepo, cRepo);

        var conductor = new UsuarioConductorEntity(); conductor.setId(7L);
        var ciudad = new CiudadEntity(); ciudad.setId(1L);
        Mockito.when(uRepo.findById(7L)).thenReturn(Optional.of(conductor));
        Mockito.when(cRepo.findById(1L)).thenReturn(Optional.of(ciudad));
        Mockito.when(vRepo.save(Mockito.any(VehiculoEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var out = svc.rf4RegistrarVehiculo(7L,1L,"ABC123", TipoVehiculo.CARRO,"Mazda","3","Rojo","Bogotá",5);
        assertEquals("ABC123", out.getPlaca());
        assertEquals(TipoVehiculo.CARRO, out.getTipo());
    }
}
