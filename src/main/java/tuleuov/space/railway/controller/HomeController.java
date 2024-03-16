package tuleuov.space.railway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tuleuov.space.railway.dto.*;
import tuleuov.space.railway.entity.*;
import tuleuov.space.railway.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class HomeController {
    private final UserService service;
    private final TrainService trainService;
    private final RouteService routeService;
    private final StationService stationService;
    private  final CarriageService carriageService;
    private final SeatService seatService;
    @PostMapping("/get-seat")
    @PreAuthorize("permitAll()")
    public Seat getSeat(@RequestBody SeatByIdAndCAttiageIdRequest request) {return  seatService.getSeatByIdAndCarriageId(request.getSeatId(), request.getCarriageId());}
    @GetMapping("/get-trains")
    @PreAuthorize("permitAll()")
    public List<Train> getTrains() {return trainService.getAll();}

    @GetMapping("/get-stations")
    @PreAuthorize("permitAll()")
    public List<Station> getStations() {return stationService.getAll();}

    @PostMapping("/carriages-by-train")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Carriage>> getCarriagesByTrainId(@RequestBody CarriageGetByIdRequest carriage) {
        List<Carriage> carriages = carriageService.getCarriagesByTrainId(carriage.getTrainId());
        if (carriages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carriages);
    }
    @PostMapping("/seats-by-carriage")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Seat>> getSeatsByCarriageId(@RequestBody CarriageGetByIdRequest carriage) {
        List<Seat> seats = seatService.getSeatsByCarriageId(carriage.getTrainId());
        if (seats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/get-route-by-name")
    @PreAuthorize("permitAll()")
    public List<Route> getRouteByName(@RequestBody GetRouteByNameRequest request) {
        String routeName = request.getRouteName();
        return routeService.getRoutesByRouteName(routeName);
    }
    @PostMapping("/find-by-stations")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Train>> findTrainsByStations(@RequestBody GetRouteByStationRequest request) {
        List<Route> routes = routeService.findRoutesByStationNames(request.getStationFirst(), request.getStationSecond());
        if (routes.isEmpty()) {
            return ResponseEntity.notFound().build(); // Маршруты не найдены
        }

        List<Train> trains = new ArrayList<>();
        for (Route route : routes) {
            List<Train> trainsForRoute = trainService.getTrainsByRoute(route.getId());
            trains.addAll(trainsForRoute);
        }
        return ResponseEntity.ok(trains);
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
        return "Ты админ, Гарри";
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN")
    public void getAdmin() {
        service.getAdmin();
    }
}
