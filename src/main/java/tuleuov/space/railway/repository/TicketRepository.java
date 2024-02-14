package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Ticket;


@Repository
public interface TicketRepository  extends JpaRepository<Ticket, Long> {
}
