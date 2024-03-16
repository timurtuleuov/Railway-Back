package tuleuov.space.railway.dto;

import lombok.Data;

@Data
public class SeatByIdAndCAttiageIdRequest {
    Long seatId;
    Long carriageId;
}
