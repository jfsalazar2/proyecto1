package uniandes.edu.co.proyecto.dtos;
import uniandes.edu.co.proyecto.entities.*;
import java.util.List;
import java.util.stream.Collectors;

public class Mappers {

  // Usuario
  public static UsuarioDTO toDTO(UsuarioEntity e){
    if(e==null) return null;
    return UsuarioDTO.builder()
      .cedula(e.getCedula()).nombre(e.getNombre()).email(e.getEmail())
      .telefono(e.getTelefono()).rol(e.getRol()).build();
  }
  public static UsuarioEntity toEntity(UsuarioDTO d){
    if(d==null) return null;
    return UsuarioEntity.builder()
      .cedula(d.getCedula()).nombre(d.getNombre()).email(d.getEmail())
      .telefono(d.getTelefono()).rol(d.getRol()).build();
  }

  // Vehiculo
  public static VehiculoDTO toDTO(VehiculoEntity e){
    if(e==null) return null;
    return VehiculoDTO.builder()
      .placa(e.getPlaca()).marca(e.getMarca()).modelo(e.getModelo())
      .capacidad(e.getCapacidad()).tipoVehiculo(e.getTipoVehiculo())
      .ciudadExpPlaca(e.getCiudadExpPlaca()).build();
  }
  public static VehiculoEntity toEntity(VehiculoDTO d){
    if(d==null) return null;
    return VehiculoEntity.builder()
      .placa(d.getPlaca()).marca(d.getMarca()).modelo(d.getModelo())
      .capacidad(d.getCapacidad()).tipoVehiculo(d.getTipoVehiculo())
      .ciudadExpPlaca(d.getCiudadExpPlaca()).build();
  }

  // Ciudad
  public static CiudadDTO toDTO(CiudadEntity e){
    if(e==null) return null;
    return CiudadDTO.builder().nombre(e.getNombre())
      .departamento(e.getDepartamento()).pais(e.getPais()).build();
  }
  public static CiudadEntity toEntity(CiudadDTO d){
    if(d==null) return null;
    return CiudadEntity.builder().nombre(d.getNombre())
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
  public static ServicioDTO toDTO(ServicioEntity e){
    if(e==null) return null;
    return ServicioDTO.builder()
      .id(e.getId()).cedulaConductor(e.getCedulaConductor()).cedulaPasajero(e.getCedulaPasajero())
      .placaVehiculo(e.getPlacaVehiculo()).tipo(e.getTipo())
      .fecha(e.getFecha()).longitudKm(e.getLongitudKm()).ciudad(e.getCiudad())
      .build();
  }
  public static ServicioEntity toEntity(ServicioDTO d){
    if(d==null) return null;
    return ServicioEntity.builder()
      .id(d.getId()).cedulaConductor(d.getCedulaConductor()).cedulaPasajero(d.getCedulaPasajero())
      .placaVehiculo(d.getPlacaVehiculo()).tipo(d.getTipo())
      .fecha(d.getFecha()).longitudKm(d.getLongitudKm()).ciudad(d.getCiudad())
      .build();
  }

  // Domicilio
  public static DomicilioDTO toDTO(DomicilioEntity e){
    if(e==null) return null;
    return DomicilioDTO.builder().idServicio(e.getIdServicio()).descripcion(e.getDescripcion()).build();
  }
  public static DomicilioEntity toEntity(DomicilioDTO d){
    if(d==null) return null;
    return DomicilioEntity.builder().idServicio(d.getIdServicio()).descripcion(d.getDescripcion()).build();
  }

  // Mercancia
  public static MercanciaDTO toDTO(MercanciaEntity e){
    if(e==null) return null;
    return MercanciaDTO.builder().idServicio(e.getIdServicio()).pesoKg(e.getPesoKg()).volumenM3(e.getVolumenM3()).build();
  }
  public static MercanciaEntity toEntity(MercanciaDTO d){
    if(d==null) return null;
    return MercanciaEntity.builder().idServicio(d.getIdServicio()).pesoKg(d.getPesoKg()).volumenM3(d.getVolumenM3()).build();
  }

  // Transporte
  public static TransporteDTO toDTO(TransporteEntity e){
    if(e==null) return null;
    return TransporteDTO.builder().idServicio(e.getIdServicio()).cantidadPasajeros(e.getCantidadPasajeros()).build();
  }
  public static TransporteEntity toEntity(TransporteDTO d){
    if(d==null) return null;
    return TransporteEntity.builder().idServicio(d.getIdServicio()).cantidadPasajeros(d.getCantidadPasajeros()).build();
  }

  // Disponibilidad
  public static DisponibilidadDTO toDTO(DisponibilidadEntity e){
    if(e==null) return null;
    return DisponibilidadDTO.builder().id(e.getId()).cedulaConductor(e.getCedulaConductor())
      .placaVehiculo(e.getPlacaVehiculo()).dia(e.getDia())
      .horaInicio(e.getHoraInicio()).horaFin(e.getHoraFin()).build();
  }
  public static DisponibilidadEntity toEntity(DisponibilidadDTO d){
    if(d==null) return null;
    return DisponibilidadEntity.builder().id(d.getId()).cedulaConductor(d.getCedulaConductor())
      .placaVehiculo(d.getPlacaVehiculo()).dia(d.getDia())
      .horaInicio(d.getHoraInicio()).horaFin(d.getHoraFin()).build();
  }

  // UsuarioServicio
  public static UsuarioServicioDTO toDTO(UsuarioServicioEntity e){
    if(e==null) return null;
    return UsuarioServicioDTO.builder().id(e.getId()).cedulaUsuario(e.getCedulaUsuario())
      .numTarjeta(e.getNumTarjeta()).fechaExpiracion(e.getFechaExpiracion()).franquicia(e.getFranquicia()).build();
  }
  public static UsuarioServicioEntity toEntity(UsuarioServicioDTO d){
    if(d==null) return null;
    return UsuarioServicioEntity.builder().id(d.getId()).cedulaUsuario(d.getCedulaUsuario())
      .numTarjeta(d.getNumTarjeta()).fechaExpiracion(d.getFechaExpiracion()).franquicia(d.getFranquicia()).build();
  }

  // Resena
  public static ResenaDTO toDTO(ResenaEntity e){
    if(e==null) return null;
    return ResenaDTO.builder().id(e.getId()).cedulaAutor(e.getCedulaAutor()).cedulaReceptor(e.getCedulaReceptor())
      .calificacion(e.getCalificacion()).comentario(e.getComentario()).fecha(e.getFecha()).build();
  }
  public static ResenaEntity toEntity(ResenaDTO d){
    if(d==null) return null;
    return ResenaEntity.builder().id(d.getId()).cedulaAutor(d.getCedulaAutor()).cedulaReceptor(d.getCedulaReceptor())
      .calificacion(d.getCalificacion()).comentario(d.getComentario()).fecha(d.getFecha()).build();
  }

  // Helpers
  public static <T, R> List<R> mapList(List<T> src, java.util.function.Function<T,R> f){
    return src==null? java.util.List.of(): src.stream().map(f).collect(Collectors.toList());
  }
}

