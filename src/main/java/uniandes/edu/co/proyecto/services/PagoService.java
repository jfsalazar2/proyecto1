package uniandes.edu.co.proyecto.services;

import org.springframework.stereotype.Service;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;

public interface PagoService {
    boolean tieneMedioDePagoActivo(UsuarioServicioEntity cliente);
    void cobrar(UsuarioServicioEntity cliente, int monto);
}

@Service
class PagoServiceMock implements PagoService {
    @Override
    public boolean tieneMedioDePagoActivo(UsuarioServicioEntity cliente) {
        try {
            var m = cliente.getClass().getMethod("tieneMedioDePagoActivo");
            Object r = m.invoke(cliente);
            return r instanceof Boolean && (Boolean) r;
        } catch (Exception e) {
            
            return true;
        }
    }
    @Override
    public void cobrar(UsuarioServicioEntity cliente, int monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        if (!tieneMedioDePagoActivo(cliente)) {
            throw new IllegalStateException("Medio de pago no disponible");
        }
        
    }
}

