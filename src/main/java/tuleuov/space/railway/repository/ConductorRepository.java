package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tuleuov.space.railway.entity.Conductor;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {
}
