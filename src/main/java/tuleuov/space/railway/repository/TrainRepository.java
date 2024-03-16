package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Train;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    List<Train> findByRouteId(Long routeId);
}
