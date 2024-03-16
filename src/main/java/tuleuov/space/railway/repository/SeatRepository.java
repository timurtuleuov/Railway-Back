package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Seat;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatNumberAndCarriageId(int seatNumber, Long carriageId);
    Optional<Seat> findByIdAndCarriageId(Long seatId, Long carriageId);
    boolean existsByCarriageIdAndIsOccupiedTrue(Long carriageId);
    List<Seat> findByCarriageId(Long carriageId);
}

