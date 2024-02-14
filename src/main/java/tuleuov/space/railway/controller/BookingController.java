package tuleuov.space.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuleuov.space.railway.dto.BookingRequest;
import tuleuov.space.railway.entity.Ticket;
import tuleuov.space.railway.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book-ticket")
    public ResponseEntity<String> bookTicketWithPayment(@RequestBody BookingRequest request) {
        try {
            Ticket bookedTicket = bookingService.bookTicketWithPayment(request.getAmount(), request.getTrainId(), request.getRouteId(), request.getUserId());
            return ResponseEntity.ok("Билетик куплен. ID Билета: " + bookedTicket.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Билетик не получилось купить: " + e.getMessage());
        }
    }
}

