package tuleuov.space.railway.dto;

import lombok.Data;

@Data
public class SeatRequest {
    private Long id;
    private int seatNumber;
    private double price;
    private boolean occupied;
}
