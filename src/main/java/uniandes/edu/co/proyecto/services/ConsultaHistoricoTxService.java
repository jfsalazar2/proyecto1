package uniandes.edu.co.proyecto.services;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.dtos.ServicioResumenDTO;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaHistoricoTxService {

    private final ServicioRepository servicioRepo;

    public ConsultaHistoricoTxService(ServicioRepository servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    // ===== RFC1 – READ_COMMITTED =====
    // Ejecuta la consulta, espera 30s y vuelve a consultar (misma transacción)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 60)
    public List<List<ServicioResumenDTO>> rfc1ReadCommitted(Long clienteId, boolean usarNativo) {
        List<ServicioEntity> primera = ejecutarConsulta(clienteId, usarNativo);
        dormir30s();
        List<ServicioEntity> segunda  = ejecutarConsulta(clienteId, usarNativo);

        List<List<ServicioResumenDTO>> resultado = new ArrayList<>(2);
        resultado.add(map(primera));
        resultado.add(map(segunda));
        return resultado;
    }

    // ===== RFC1 – SERIALIZABLE =====
    // Ejecuta la consulta, espera 30s y vuelve a consultar (misma transacción)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE, timeout = 60)
    public List<List<ServicioResumenDTO>> rfc1Serializable(Long clienteId, boolean usarNativo) {
        List<ServicioEntity> primera = ejecutarConsulta(clienteId, usarNativo);
        dormir30s();
        List<ServicioEntity> segunda  = ejecutarConsulta(clienteId, usarNativo);

        List<List<ServicioResumenDTO>> resultado = new ArrayList<>(2);
        resultado.add(map(primera));
        resultado.add(map(segunda));
        return resultado;
    }

    // ---- Helpers ----
    private List<ServicioEntity> ejecutarConsulta(Long clienteId, boolean usarNativo) {
        if (usarNativo) {
            return servicioRepo.historicoClienteNative(clienteId);
        }
        return servicioRepo.findByClienteIdOrderByFechaDescHoraInicioDesc(clienteId);
    }

    private void dormir30s() {
        try {
            Thread.sleep(30_000L); // 30 segundos
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private List<ServicioResumenDTO> map(List<ServicioEntity> lista) {
        List<ServicioResumenDTO> out = new ArrayList<>(lista.size());
        for (var s : lista) {
            out.add(new ServicioResumenDTO(
                s.getId(), s.getTipo(), s.getCosto(), s.getDuracion(),
                s.getFecha(), s.getHoraInicio(), s.getHoraFin(),
                s.getConductor() != null ? s.getConductor().getId() : null,
                s.getConductor() != null ? s.getConductor().getNombre() : null,
                s.getVehiculo() != null ? s.getVehiculo().getId() : null,
                s.getVehiculo() != null ? s.getVehiculo().getPlaca() : null, null, null, null, null
            ));
        }
        return out;
    }
}
