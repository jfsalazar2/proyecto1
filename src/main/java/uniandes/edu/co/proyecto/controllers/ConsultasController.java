package uniandes.edu.co.proyecto.controllers;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniandes.edu.co.proyecto.dtos.*;
import uniandes.edu.co.proyecto.services.ConsultaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultasController {

    private final ConsultaService service;

    public ConsultasController(ConsultaService service) {
        this.service = service;
    }

    // RFC1
    @GetMapping("/historico/cliente/{clienteId}")
    public ResponseEntity<List<ServicioResumenDTO>> historicoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.rfc1HistoricoCliente(clienteId));
    }

    // RFC2
    @GetMapping("/top-conductores")
    public ResponseEntity<List<TopConductorDTO>> topConductores() {
        return ResponseEntity.ok(service.rfc2Top20Conductores());
    }

    // RFC3  
    @GetMapping("/totales-vehiculo/{conductorId}")
    public ResponseEntity<List<TotalesVehiculoDTO>> totalesVehiculo(@PathVariable Long conductorId,
                                                                     @RequestParam(defaultValue = "0.15") double comision) {
        return ResponseEntity.ok(service.rfc3TotalesPorVehiculo(conductorId, comision));
    }

    // RFC4 
    @GetMapping("/uso-ciudad/{ciudadId}")
    public ResponseEntity<List<UsoServicioDTO>> usoCiudadRango(@PathVariable Long ciudadId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ini,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(service.rfc4UsoPorCiudadYRango(ciudadId, ini, fin));
    }
}
