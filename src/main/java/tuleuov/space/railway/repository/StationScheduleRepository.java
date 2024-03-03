package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tuleuov.space.railway.entity.StationSchedule;

public interface StationScheduleRepository extends JpaRepository<StationSchedule, Long> {
}
