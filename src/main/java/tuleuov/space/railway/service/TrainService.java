package tuleuov.space.railway.service;

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

    public Train createTrain() {
        Train newTrain = new Train();
        return trainRepository.save(newTrain);
    }

    public Train getTrainById(Long trainId) {
        Train train = trainRepository.getById(trainId);
        return train;
    }

    public void deleteTrain(Long trainId) {
        trainRepository.deleteById(trainId);
    }


    public List<Train> getAll() {
        return trainRepository.findAll();
    }
}
