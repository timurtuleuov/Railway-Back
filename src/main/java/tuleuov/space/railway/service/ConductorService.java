package tuleuov.space.railway.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Conductor;
import tuleuov.space.railway.repository.ConductorRepository;

import java.util.List;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;

    @Autowired
    public ConductorService(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    public List<Conductor> getAllConductors() {
        return conductorRepository.findAll();
    }

    public Conductor getConductorById(Long id) {
        return conductorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Проводник с " + id + " не найден"));
    }

    public Conductor createConductor(Conductor conductor) {
        return conductorRepository.save(conductor);
    }

    public void deleteConductor(Long id) {
        if (conductorRepository.existsById(id)) {
            conductorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Проводник с " + id + " не найден");
        }
    }
}
