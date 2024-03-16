package tuleuov.space.railway.dto;

import lombok.Data;

@Data
public class SeatDTO {
    private Long id;
    private int seatNumber;
    private int price;
    private boolean occupied;
}
