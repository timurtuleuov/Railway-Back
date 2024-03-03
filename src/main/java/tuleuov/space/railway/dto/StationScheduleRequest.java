package tuleuov.space.railway.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StationScheduleRequest {
    private Long stationId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}
