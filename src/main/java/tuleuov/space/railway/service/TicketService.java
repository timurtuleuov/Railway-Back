package tuleuov.space.railway.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Ticket;
import tuleuov.space.railway.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    @Transactional
    public Ticket updateTicket(Ticket ticket) {
        if (ticket.getId() == null) {
            throw new IllegalArgumentException("Ticket must have an ID for update.");
        }

        return ticketRepository.save(ticket);
    }
}

