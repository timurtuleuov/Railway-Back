package tuleuov.space.railway.dto;

import lombok.Data;

@Data
public class GetRouteByStationRequest {
    String stationFirst;
    String stationSecond;
}
