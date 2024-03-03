package tuleuov.space.railway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuleuov.space.railway.dto.GetRouteByNameRequest;
import tuleuov.space.railway.dto.GetRouteByStationRequest;
import tuleuov.space.railway.dto.RouteRequest;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.Station;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.service.RouteService;
import tuleuov.space.railway.service.StationService;
import tuleuov.space.railway.service.TrainService;
import tuleuov.space.railway.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class HomeController {
    private final UserService service;
    private final TrainService trainService;
    private final RouteService routeService;
    private final StationService stationService;
    @GetMapping("/get-trains")
    @PreAuthorize("permitAll()")
    public List<Train> getTrains() {return trainService.getAll();}

    @GetMapping("/get-stations")
    @PreAuthorize("permitAll()")
    public List<Station> getStations() {return stationService.getAll();}

    @GetMapping("/get-route-by-name")
    @PreAuthorize("permitAll()")
    public List<Route> getRouteByName(@RequestBody GetRouteByNameRequest request) {
        String routeName = request.getRouteName();
        return routeService.getRoutesByRouteName(routeName);
    }
    @GetMapping("/find-by-stations")
    public ResponseEntity<List<Route>> findRoutesByStations(@RequestBody GetRouteByStationRequest request) {
        List<Route> routes = routeService.findRoutesByStationNames(request.getStationFirst(), request.getStationSecond());
        if (routes.isEmpty()) {
            return ResponseEntity.notFound().build(); // Маршруты не найдены
        }
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/get-routes")
    @PreAuthorize("permitAll()")
    public List<Route> getRoutes() {return routeService.getAll();}
    @GetMapping("/test")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String example() {
        return "Hello, world!";
    }

    @GetMapping("/admin")
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public String exampleAdmin() {
        return "Hello, admin!";
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN (для демонстрации)")
    public void getAdmin() {
        service.getAdmin();
    }
}
