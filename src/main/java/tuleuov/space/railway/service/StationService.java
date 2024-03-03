package tuleuov.space.railway.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Station;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.repository.StationRepository;

import java.util.List;

@Service
public class StationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    public Station getStationById(Long stationId) {
        Station station = stationRepository.getById(stationId);
        return station;
    }

    public void deleteStation(Long stationId) {
        stationRepository.deleteById(stationId);
    }


    public List<Station> getAll() {
        return stationRepository.findAll();
    }

    @Transactional
    public Station updateStation(Long stationId, Station updatedStation) {
        Station existingStation = getStationById(stationId);
        existingStation.setStationName(updatedStation.getStationName());
        // Обновление других полей по необходимости

        return stationRepository.save(existingStation);
    }

    public Long findStationIdByName(String stationName) {
        Station station = stationRepository.findByStationName(stationName);
        return station != null ? station.getId() : null;
    }
}
