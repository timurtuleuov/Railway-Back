package tuleuov.space.railway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuleuov.space.railway.entity.*;
import tuleuov.space.railway.repository.RouteRepository;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final TicketService ticketService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final TrainService trainService;
    private final RouteService routeService;

    @Autowired
    public BookingService(TicketService ticketService, PaymentService paymentService, UserService userService, TrainService trainService, RouteService routeService) {
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.trainService = trainService;
        this.routeService = routeService;
    }

    @Transactional
    public Ticket bookTicketWithPayment(double amount, Long trainId, Long routeId, Long userId) {

        User user = userService.getUserById(userId);


        Ticket ticket = new Ticket();

        Train train = trainService.getTrainById(trainId);
        Route route = routeService.getRouteById(routeId);

        ticket = ticketService.createTicket(ticket);

        Payment payment = paymentService.processPayment(amount, ticket.getId(), user.getId());

        ticket.setTrain(train);
        ticket.setPurchaseTime(LocalDateTime.now());
        ticket.setRoute(route);
        ticket.setUser(user);

        ticketService.updateTicket(ticket);

        return ticket;
    }

}


