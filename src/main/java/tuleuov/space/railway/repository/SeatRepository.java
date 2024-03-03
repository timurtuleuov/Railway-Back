package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}

