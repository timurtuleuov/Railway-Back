package tuleuov.space.railway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RouteRequest {
    private String routeName;
    private List<StationScheduleRequest> stationSchedules;
}

