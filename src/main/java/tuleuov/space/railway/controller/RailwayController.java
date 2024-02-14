package tuleuov.space.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tuleuov.space.railway.dto.RouteRequest;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.service.RouteService;
import tuleuov.space.railway.service.TrainService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/railway")
public class RailwayController {
    private final TrainService trainService;
    private final RouteService routeService;

    @Autowired
    public RailwayController(TrainService trainService, RouteService routeService) {
        this.trainService = trainService;
        this.routeService = routeService;
    }


    @PostMapping("/create-train")
    public Train createTrain() {
        return trainService.createTrain();
    }

    @PostMapping("/create-route")
    public Route createRoute(@RequestBody RouteRequest request) {
        Route route = new Route();
        route.setRouteName(request.getRouteName());
        return routeService.createRoute(route);
    }
    @DeleteMapping("/delete-train/{trainId}")
    public ResponseEntity<String> deleteTrain(@PathVariable Long trainId) {
        Optional<Train> trainOptional = Optional.ofNullable(trainService.getTrainById(trainId));

        if (trainOptional.isPresent()) {
            trainService.deleteTrain(trainId);
            return ResponseEntity.ok("Поезд с id " + trainId + " удален");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Поезд с id " + trainId + " не найден");
        }
    }
    @DeleteMapping("/delete-route/{routeId}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long routeId) {
        try {
            routeService.deleteRouteById(routeId);
            return ResponseEntity.ok("Маршрут с id " + routeId + " был удален.");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Маршрут с id " + routeId + " не найден.");
        }
    }


}
