package uniandes.edu.co.proyecto.services;
import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;
import uniandes.edu.co.proyecto.exceptions.NotFoundException;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.PuntoGeograficoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuntoGeograficoService {

    
    private final PuntoGeograficoRepository repo;
    private final CiudadRepository ciudadRepo;

    public PuntoGeograficoService(PuntoGeograficoRepository repo, CiudadRepository ciudadRepo) {
        this.repo = repo; this.ciudadRepo = ciudadRepo;
    }

    @Transactional
    public PuntoGeograficoEntity rf7RegistrarPunto(String nombre, String direccion, String coordenadas, Long ciudadId) {
        CiudadEntity ciudad = ciudadRepo.findById(ciudadId)
                .orElseThrow(() -> new NotFoundException("Ciudad no encontrada"));
        PuntoGeograficoEntity p = new PuntoGeograficoEntity();
        p.setNombre(nombre);
        p.setDireccion(direccion);
        p.setCoordenadas(coordenadas);
        p.setCiudad(ciudad);
        return repo.save(p);
    }
}
