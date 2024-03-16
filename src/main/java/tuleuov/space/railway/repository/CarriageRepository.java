package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Carriage;

import java.util.List;

@Repository
public interface CarriageRepository extends JpaRepository<Carriage, Long> {
    List<Carriage> findAllByTrainId(Long trainId);
}
