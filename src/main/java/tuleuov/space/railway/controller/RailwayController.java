package tuleuov.space.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tuleuov.space.railway.dto.RouteRequest;
import tuleuov.space.railway.dto.StationRequest;
import tuleuov.space.railway.dto.StationScheduleRequest;
import tuleuov.space.railway.dto.TrainRequest;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.Station;
import tuleuov.space.railway.entity.StationSchedule;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.service.RouteService;
import tuleuov.space.railway.service.StationScheduleService;
import tuleuov.space.railway.service.StationService;
import tuleuov.space.railway.service.TrainService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/railway")
public class RailwayController {
    private final TrainService trainService;
    private final RouteService routeService;
    private final StationService stationService;
    private final StationScheduleService stationScheduleService;

    @Autowired
    public RailwayController(TrainService trainService, RouteService routeService, StationService stationService, StationScheduleService stationScheduleService) {
        this.trainService = trainService;
        this.routeService = routeService;
        this.stationService = stationService;
        this.stationScheduleService = stationScheduleService;
    }


    @PostMapping("/create-train")
    public Train createTrain(@RequestBody TrainRequest request) {
        Train train = new Train();
        train.setTrainName(request.getTrainName());
        return trainService.createTrain(train);
    }
    @PostMapping("/create-station")
    public Station createStation(@RequestBody StationRequest request) {
        Station station = new Station();
        station.setStationName(request.getStationName());
        return stationService.createStation(station);
    }

    @PostMapping("/create-route")
    public ResponseEntity<?> createRoute(@RequestBody RouteRequest request) {
        Route route = new Route();
        route.setRouteName(request.getRouteName());

        // Создание и установка расписаний станций
        Set<StationSchedule> stationSchedules = new HashSet<>();
        for (StationScheduleRequest scheduleRequest : request.getStationSchedules()) {
            StationSchedule stationSchedule = new StationSchedule();
            stationSchedule.setArrivalTime(scheduleRequest.getArrivalTime());
            stationSchedule.setDepartureTime(scheduleRequest.getDepartureTime());

            // Получение станции по её идентификатору из запроса и установка расписания
            Station station = stationService.getStationById(scheduleRequest.getStationId());
            if (station != null) {
                stationSchedule.setStation(station);
                stationSchedules.add(stationSchedule);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        route.setStationSchedules(stationSchedules);

        Route createdRoute = routeService.createRoute(route);

        if (createdRoute != null) {
            // Инициализация маршрута перед возвратом
            createdRoute = routeService.initializeRoute(createdRoute);
            return ResponseEntity.ok(createdRoute);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    @PutMapping("/update-train/{trainId}")
    public ResponseEntity<String> updateTrain(@PathVariable Long trainId, @RequestBody TrainRequest request) {
        Train existingTrain = trainService.getTrainById(trainId);
        if (existingTrain != null) {
            existingTrain.setTrainName(request.getTrainName());
            // Обновление других полей, если необходимо
            trainService.updateTrain(trainId, existingTrain);
            return ResponseEntity.ok("Поезд с id " + trainId + " успешно обновлен");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Поезд с id " + trainId + " не найден");
        }
    }

    // Обновление информации о станции
    @PutMapping("/update-station/{stationId}")
    public ResponseEntity<String> updateStation(@PathVariable Long stationId, @RequestBody StationRequest request) {
        Station existingStation = stationService.getStationById(stationId);
        if (existingStation != null) {
            existingStation.setStationName(request.getStationName());
            // Обновление других полей, если необходимо
            stationService.updateStation(stationId, existingStation);
            return ResponseEntity.ok("Станция с id " + stationId + " успешно обновлена");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Станция с id " + stationId + " не найдена");
        }
    }

    @PutMapping("/update-route/{routeId}")
    public ResponseEntity<String> updateRoute(@PathVariable Long routeId, @RequestBody RouteRequest request) {
        Route existingRoute = routeService.getRouteById(routeId);
        if (existingRoute != null) {
            existingRoute.setRouteName(request.getRouteName());

            // Получение расписаний станций из запроса
            Set<StationSchedule> stationSchedules = new HashSet<>();
            for (StationScheduleRequest scheduleRequest : request.getStationSchedules()) {
                StationSchedule stationSchedule = new StationSchedule();
                stationSchedule.setId(scheduleRequest.getStationId());
                stationSchedule.setArrivalTime(scheduleRequest.getArrivalTime());
                stationSchedule.setDepartureTime(scheduleRequest.getDepartureTime());
                // Другие необходимые операции для создания расписания станции
                stationSchedules.add(stationSchedule);
            }
            existingRoute.setStationSchedules(stationSchedules);

            routeService.updateRoute(existingRoute);
            return ResponseEntity.ok("Маршрут с id " + routeId + " успешно обновлен");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Маршрут с id " + routeId + " не найден");
        }
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
    @DeleteMapping("/delete-station/{stationId}")
    public ResponseEntity<String> deleteStation(@PathVariable Long stationId) {
        Optional<Station> stationOptional = Optional.ofNullable(stationService.getStationById(stationId));

        if (stationOptional.isPresent()) {
            stationService.deleteStation(stationId);
            return ResponseEntity.ok("Станция с id " + stationId + " удалена");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Станция с id " + stationId + " не найдена");
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
