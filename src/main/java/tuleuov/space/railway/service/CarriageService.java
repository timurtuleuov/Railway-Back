package tuleuov.space.railway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Carriage;
import tuleuov.space.railway.entity.Seat;
import tuleuov.space.railway.repository.CarriageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarriageService {

    private final CarriageRepository carriageRepository;

    @Autowired
    public CarriageService(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }

    public List<Carriage> getCarriagesByTrainId(Long trainId) {
        return carriageRepository.findAllByTrainId(trainId);
    }

    public Carriage getCarriageById(Long carriageId) {
        return carriageRepository.findById(carriageId).orElse(null);
    }
    public Carriage createCarriage(Carriage carriage) {
        return carriageRepository.save(carriage);
    }

    public Carriage updateCarriageStatus(Carriage carriage) throws CarriageNotFoundException {
        Optional<Carriage> optionalCarriage = carriageRepository.findById(carriage.getId());
        if (optionalCarriage.isPresent()) {
            Carriage existingCarriage = optionalCarriage.get();
            existingCarriage.setStatus(carriage.isStatus());
            return carriageRepository.save(existingCarriage);
        } else {
            throw new CarriageNotFoundException("Carriage with id " + carriage.getId() + " not found.");
        }
    }
    public Carriage updateCarriageSeats(Long carriageId, List<Seat> updatedSeats) {
        Carriage carriage = getCarriageById(carriageId);
        if (carriage != null) {
            // Обновление сидений в вагоне
            carriage.getSeats().clear();
            carriage.getSeats().addAll(updatedSeats);

            // Сохранение обновленного вагона
            return carriageRepository.save(carriage);
        } else {
            return null;
        }
    }
    public Carriage updateCarriage(Carriage carriage) {
        return carriageRepository.save(carriage);
    }
}

