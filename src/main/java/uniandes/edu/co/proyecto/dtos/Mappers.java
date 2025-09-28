package uniandes.edu.co.proyecto.dtos;
import uniandes.edu.co.proyecto.entities.*;
import java.util.List;
import java.util.stream.Collectors;

public class Mappers {

  // Usuario
  public static UsuarioDTO toDTO(Usuario e){
    if(e==null) return null;
    return UsuarioDTO.builder()
      .cedula(e.getCedula()).nombre(e.getNombre()).email(e.getEmail())
      .telefono(e.getTelefono()).rol(e.getRol()).build();
  }
  public static Usuario toEntity(UsuarioDTO d){
    if(d==null) return null;
    return Usuario.builder()
      .cedula(d.getCedula()).nombre(d.getNombre()).email(d.getEmail())
      .telefono(d.getTelefono()).rol(d.getRol()).build();
  }

  // Vehiculo
  public static VehiculoDTO toDTO(Vehiculo e){
    if(e==null) return null;
    return VehiculoDTO.builder()
      .placa(e.getPlaca()).marca(e.getMarca()).modelo(e.getModelo())
      .capacidad(e.getCapacidad()).tipoVehiculo(e.getTipoVehiculo())
      .ciudadExpPlaca(e.getCiudadExpPlaca()).build();
  }
  public static Vehiculo toEntity(VehiculoDTO d){
    if(d==null) return null;
    return Vehiculo.builder()
      .placa(d.getPlaca()).marca(d.getMarca()).modelo(d.getModelo())
      .capacidad(d.getCapacidad()).tipoVehiculo(d.getTipoVehiculo())
      .ciudadExpPlaca(d.getCiudadExpPlaca()).build();
  }

  // Ciudad
  public static CiudadDTO toDTO(Ciudad e){
    if(e==null) return null;
    return CiudadDTO.builder().nombre(e.getNombre())
      .departamento(e.getDepartamento()).pais(e.getPais()).build();
  }
  public static Ciudad toEntity(CiudadDTO d){
    if(d==null) return null;
    return Ciudad.builder().nombre(d.getNombre())
      .departamento(d.getDepartamento()).pais(d.getPais()).build();
  }

  // TipoVehiculo
  public static TipoVehiculoDTO toDTO(TipoVehiculo e){
    if(e==null) return null;
    return TipoVehiculoDTO.builder().tipo(e.getTipo()).descripcion(e.getDescripcion()).build();
  }
  public static TipoVehiculo toEntity(TipoVehiculoDTO d){
    if(d==null) return null;
    return TipoVehiculo.builder().tipo(d.getTipo()).descripcion(d.getDescripcion()).build();
  }

  // NivelVehiculo
  public static NivelVehiculoDTO toDTO(NivelVehiculo e){
    if(e==null) return null;
    return NivelVehiculoDTO.builder().nivel(e.getNivel()).tarifaDelNivel(e.getTarifaDelNivel()).build();
  }
  public static NivelVehiculo toEntity(NivelVehiculoDTO d){
    if(d==null) return null;
    return NivelVehiculo.builder().nivel(d.getNivel()).tarifaDelNivel(d.getTarifaDelNivel()).build();
  }

  // TipoServicio
  public static TipoServicioDTO toDTO(TipoServicio e){
    if(e==null) return null;
    return TipoServicioDTO.builder().tipoServicio(e.getTipoServicio()).tarifa(e.getTarifa()).build();
  }
  public static TipoServicio toEntity(TipoServicioDTO d){
    if(d==null) return null;
    return TipoServicio.builder().tipoServicio(d.getTipoServicio()).tarifa(d.getTarifa()).build();
  }

  // Servicio
  public static ServicioDTO toDTO(Servicio e){
    if(e==null) return null;
    return ServicioDTO.builder()
      .id(e.getId()).cedulaConductor(e.getCedulaConductor()).cedulaPasajero(e.getCedulaPasajero())
      .placaVehiculo(e.getPlacaVehiculo()).tipo(e.getTipo())
      .fecha(e.getFecha()).longitudKm(e.getLongitudKm()).ciudad(e.getCiudad())
      .build();
  }
  public static Servicio toEntity(ServicioDTO d){
    if(d==null) return null;
    return Servicio.builder()
      .id(d.getId()).cedulaConductor(d.getCedulaConductor()).cedulaPasajero(d.getCedulaPasajero())
      .placaVehiculo(d.getPlacaVehiculo()).tipo(d.getTipo())
      .fecha(d.getFecha()).longitudKm(d.getLongitudKm()).ciudad(d.getCiudad())
      .build();
  }

  // Domicilio
  public static DomicilioDTO toDTO(Domicilio e){
    if(e==null) return null;
    return DomicilioDTO.builder().idServicio(e.getIdServicio()).descripcion(e.getDescripcion()).build();
  }
  public static Domicilio toEntity(DomicilioDTO d){
    if(d==null) return null;
    return Domicilio.builder().idServicio(d.getIdServicio()).descripcion(d.getDescripcion()).build();
  }

  // Mercancia
  public static MercanciaDTO toDTO(Mercancia e){
    if(e==null) return null;
    return MercanciaDTO.builder().idServicio(e.getIdServicio()).pesoKg(e.getPesoKg()).volumenM3(e.getVolumenM3()).build();
  }
  public static Mercancia toEntity(MercanciaDTO d){
    if(d==null) return null;
    return Mercancia.builder().idServicio(d.getIdServicio()).pesoKg(d.getPesoKg()).volumenM3(d.getVolumenM3()).build();
  }

  // Transporte
  public static TransporteDTO toDTO(Transporte e){
    if(e==null) return null;
    return TransporteDTO.builder().idServicio(e.getIdServicio()).cantidadPasajeros(e.getCantidadPasajeros()).build();
  }
  public static Transporte toEntity(TransporteDTO d){
    if(d==null) return null;
    return Transporte.builder().idServicio(d.getIdServicio()).cantidadPasajeros(d.getCantidadPasajeros()).build();
  }

  // Disponibilidad
  public static DisponibilidadDTO toDTO(Disponibilidad e){
    if(e==null) return null;
    return DisponibilidadDTO.builder().id(e.getId()).cedulaConductor(e.getCedulaConductor())
      .placaVehiculo(e.getPlacaVehiculo()).dia(e.getDia())
      .horaInicio(e.getHoraInicio()).horaFin(e.getHoraFin()).build();
  }
  public static Disponibilidad toEntity(DisponibilidadDTO d){
    if(d==null) return null;
    return Disponibilidad.builder().id(d.getId()).cedulaConductor(d.getCedulaConductor())
      .placaVehiculo(d.getPlacaVehiculo()).dia(d.getDia())
      .horaInicio(d.getHoraInicio()).horaFin(d.getHoraFin()).build();
  }

  // UsuarioServicio
  public static UsuarioServicioDTO toDTO(UsuarioServicio e){
    if(e==null) return null;
    return UsuarioServicioDTO.builder().id(e.getId()).cedulaUsuario(e.getCedulaUsuario())
      .numTarjeta(e.getNumTarjeta()).fechaExpiracion(e.getFechaExpiracion()).franquicia(e.getFranquicia()).build();
  }
  public static UsuarioServicio toEntity(UsuarioServicioDTO d){
    if(d==null) return null;
    return UsuarioServicio.builder().id(d.getId()).cedulaUsuario(d.getCedulaUsuario())
      .numTarjeta(d.getNumTarjeta()).fechaExpiracion(d.getFechaExpiracion()).franquicia(d.getFranquicia()).build();
  }

  // Resena
  public static ResenaDTO toDTO(Resena e){
    if(e==null) return null;
    return ResenaDTO.builder().id(e.getId()).cedulaAutor(e.getCedulaAutor()).cedulaReceptor(e.getCedulaReceptor())
      .calificacion(e.getCalificacion()).comentario(e.getComentario()).fecha(e.getFecha()).build();
  }
  public static Resena toEntity(ResenaDTO d){
    if(d==null) return null;
    return Resena.builder().id(d.getId()).cedulaAutor(d.getCedulaAutor()).cedulaReceptor(d.getCedulaReceptor())
      .calificacion(d.getCalificacion()).comentario(d.getComentario()).fecha(d.getFecha()).build();
  }

  // Helpers
  public static <T, R> List<R> mapList(List<T> src, java.util.function.Function<T,R> f){
    return src==null? java.util.List.of(): src.stream().map(f).collect(Collectors.toList());
  }
}

