package tuleuov.space.railway.dto;

import lombok.Data;

@Data
public class TrainCreateRequest {
    private String trainName;
    private Long routeId;
}
