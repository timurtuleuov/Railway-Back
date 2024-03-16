package tuleuov.space.railway.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarriageRequest {
    private Long id;
    private String type;
    private int numberCarriage;
    private List<SeatRequest> seats;
}
