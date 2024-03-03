package tuleuov.space.railway.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuleuov.space.railway.entity.StationSchedule;
import tuleuov.space.railway.repository.StationScheduleRepository;

import java.util.List;
import java.util.Optional;
@Service
public class StationScheduleService {

    private final StationScheduleRepository stationScheduleRepository;

    @Autowired
    public StationScheduleService(StationScheduleRepository stationScheduleRepository) {
        this.stationScheduleRepository = stationScheduleRepository;
    }

    @Transactional
    public StationSchedule createStationSchedule(StationSchedule stationSchedule) {
        return stationScheduleRepository.save(stationSchedule);
    }

    @Transactional(readOnly = true)
    public StationSchedule getStationScheduleById(Long stationScheduleId) {
        Optional<StationSchedule> optionalStationSchedule = stationScheduleRepository.findById(stationScheduleId);
        return optionalStationSchedule.orElse(null);
    }

    @Transactional
    public void deleteStationSchedule(Long stationScheduleId) {
        stationScheduleRepository.deleteById(stationScheduleId);
    }

    @Transactional(readOnly = true)
    public List<StationSchedule> getAllStationSchedules() {
        return stationScheduleRepository.findAll();
    }
}