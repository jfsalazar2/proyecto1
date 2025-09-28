package uniandes.edu.co.proyecto.controllers;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/consultas")
public class ConsultasController {

  private final ServicioRepository repo;
  public ConsultasController(ServicioRepository repo){ this.repo = repo; }

  @GetMapping("/rfc1-ingresos-por-vehiculo")
  public List<Map<String,Object>> rfc1(){
    List<Map<String,Object>> out = new ArrayList<>();
    for(Object[] r : repo.rfcIngresosPorVehiculo()){
      Map<String,Object> m = new LinkedHashMap<>();
      m.put("placaVehiculo", String.valueOf(r[0]));
      m.put("total", r[1]==null?0.0:((Number)r[1]).doubleValue());
      out.add(m);
    }
    return out;
  }

  @GetMapping("/rfc2-ingresos-por-tipo")
  public List<Map<String,Object>> rfc2(){
    List<Map<String,Object>> out = new ArrayList<>();
    for(Object[] r : repo.rfcIngresosPorTipo()){
      Map<String,Object> m = new LinkedHashMap<>();
      m.put("tipoServicio", String.valueOf(r[0]));
      m.put("total", r[1]==null?0.0:((Number)r[1]).doubleValue());
      out.add(m);
    }
    return out;
  }

  @GetMapping("/rfc3-top-vehiculos")
  public List<Map<String,Object>> rfc3(
      @RequestParam(required=false) String ciudad,
      @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate desde,
      @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate hasta){
    List<Map<String,Object>> out = new ArrayList<>();
    for(Object[] r : repo.rfcTopVehiculos(ciudad, Date.valueOf(desde), Date.valueOf(hasta))){
      Map<String,Object> m = new LinkedHashMap<>();
      m.put("placaVehiculo", String.valueOf(r[0]));
      m.put("total", r[1]==null?0.0:((Number)r[1]).doubleValue());
      out.add(m);
    }
    return out;
  }
}
