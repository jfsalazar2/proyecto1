package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServicioEntity;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaHistoricoService {
    private final ServicioRepository servicioRepo;
    private final UsuarioServicioRepository clienteRepo;

    public ConsultaHistoricoService(ServicioRepository servicioRepo, UsuarioServicioRepository clienteRepo) {
        this.servicioRepo = servicioRepo; this.clienteRepo = clienteRepo;
    }

    // Asume método en repo: List<ServicioEntity> findByCliente(UsuarioServicioEntity cliente)
    public List<ServicioEntity> rfc1HistoricoPorCliente(Long clienteId) {
        UsuarioServicioEntity u = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return servicioRepo.findByCliente(u);
    }
}
