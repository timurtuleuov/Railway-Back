package tuleuov.space.railway.controller;

import org.springframework.web.bind.annotation.*;
import tuleuov.space.railway.dto.RouteRequest;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.service.RouteService;
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
    @GetMapping("/get-trains")
    @PreAuthorize("permitAll()")
    public List<Train> getTrains() {return trainService.getAll();}

    @GetMapping("/get-route-by-name")
    @PreAuthorize("permitAll()")
    public Route getRouteByName(@RequestBody RouteRequest routeName) {
        return routeService.getRouteByRouteName(routeName.getRouteName());
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
