package tuleuov.space.railway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на билетик")
public class BookingRequest {
    private double amount;
    private String phone;
    private Long routeId;
    private Long trainId;
}

