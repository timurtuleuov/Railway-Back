package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.Station;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByStationName(String stationName);
}
