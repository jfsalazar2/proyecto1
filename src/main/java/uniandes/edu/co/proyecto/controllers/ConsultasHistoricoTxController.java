package uniandes.edu.co.proyecto.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.dtos.ServicioResumenDTO;
import uniandes.edu.co.proyecto.services.ConsultaHistoricoTxService;

import java.util.List;

@RestController
@RequestMapping("/api/consultas/historico-tx")
public class ConsultasHistoricoTxController {

    private final ConsultaHistoricoTxService service;

    public ConsultasHistoricoTxController(ConsultaHistoricoTxService service) {
        this.service = service;
    }

    // RFC1 con READ_COMMITTED
    // GET /api/consultas/historico-tx/read-committed/{clienteId}?native=false
    @GetMapping("/read-committed/{clienteId}")
    public ResponseEntity<List<List<ServicioResumenDTO>>> historicoReadCommitted(
            @PathVariable Long clienteId,
            @RequestParam(defaultValue = "false") boolean nativeQuery
    ) {
        return ResponseEntity.ok(service.rfc1ReadCommitted(clienteId, nativeQuery));
    }

    // RFC1 con SERIALIZABLE
    // GET /api/consultas/historico-tx/serializable/{clienteId}?native=false
    @GetMapping("/serializable/{clienteId}")
    public ResponseEntity<List<List<ServicioResumenDTO>>> historicoSerializable(
            @PathVariable Long clienteId,
            @RequestParam(defaultValue = "false") boolean nativeQuery
    ) {
        return ResponseEntity.ok(service.rfc1Serializable(clienteId, nativeQuery));
    }
}
