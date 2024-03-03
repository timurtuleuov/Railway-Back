package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tuleuov.space.railway.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
