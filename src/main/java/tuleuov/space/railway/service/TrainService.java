package tuleuov.space.railway.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Ticket;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.repository.TicketRepository;
import tuleuov.space.railway.repository.TrainRepository;

import java.util.List;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    public Train getTrainById(Long trainId) {
        Train train = trainRepository.getById(trainId);
        return train;
    }
    public List<Train> getTrainsByRoute(Long routeId) {
        return trainRepository.findByRouteId(routeId);
    }
    public void deleteTrain(Long trainId) {
        trainRepository.deleteById(trainId);
    }
    @Transactional
    public Train updateTrain(Long trainId, Train updatedTrain) {
        Train existingTrain = getTrainById(trainId);
        existingTrain.setTrainName(updatedTrain.getTrainName());

        return trainRepository.save(existingTrain);
    }

    public List<Train> getAll() {
        return trainRepository.findAll();
    }
}
