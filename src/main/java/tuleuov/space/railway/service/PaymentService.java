package tuleuov.space.railway.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Payment;
import tuleuov.space.railway.entity.Ticket;
import tuleuov.space.railway.entity.User;
import tuleuov.space.railway.repository.PaymentRepository;
import tuleuov.space.railway.repository.TicketRepository;
import tuleuov.space.railway.repository.UserRepository;

@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public PaymentService(UserRepository userRepository, PaymentRepository paymentRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Payment processPayment(double amount, Long ticketId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользоватлеь с таким id не найден: " + userId));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Билет с таким id не найден: " + ticketId));

        Payment payment = new Payment();
        payment.setAmount((int) amount);
        payment.setTicket(ticket);
        payment.setUser(user);


        return paymentRepository.save(payment);
    }
}


