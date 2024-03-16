package tuleuov.space.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tuleuov.space.railway.dto.*;
import tuleuov.space.railway.entity.*;
import tuleuov.space.railway.service.*;

import java.util.*;

@RestController
@RequestMapping("/api/railway")
public class RailwayController {
    private final TrainService trainService;
    private final RouteService routeService;
    private final StationService stationService;
    private final StationScheduleService stationScheduleService;
    private final CarriageService carriageService;
    private final SeatService seatService;
    @Autowired
    public RailwayController(TrainService trainService, RouteService routeService,
                             StationService stationService, SeatService seatService,
                             StationScheduleService stationScheduleService, CarriageService carriageService) {
        this.trainService = trainService;
        this.routeService = routeService;
        this.stationService = stationService;
        this.stationScheduleService = stationScheduleService;
        this.carriageService = carriageService;
        this.seatService = seatService;
    }


    @PostMapping("/create-train")
    public ResponseEntity<String> createTrain(@RequestBody TrainRequest request) {
        Train train = new Train();
        train.setTrainName(request.getTrainName());

        // Получаем маршрут по идентификатору, если указан
        if (request.getRouteId() != null) {
            Route route = routeService.getRouteById(request.getRouteId());
            if (route != null) {
                train.setRoute(route);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Маршрут с id " + request.getRouteId() + " не найден");
            }
        }

        // Сохраняем поезд в базе данных
        trainService.createTrain(train);

        // Создаем 5 вагонов: 2 купе, 2 плацкарта и 1 ресторан
        List<Carriage> carriages = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Carriage coupeCarriage = new Carriage();
            coupeCarriage.setType("Купе");
            coupeCarriage.setNumberCarriage(i + 1);
            coupeCarriage.setTrain(train);
            coupeCarriage.setSeats(createSeats(20, coupeCarriage));
            carriages.add(coupeCarriage);

            Carriage reservedSeatCarriage = new Carriage();
            reservedSeatCarriage.setType("Плацкарт");
            reservedSeatCarriage.setNumberCarriage(i + 1);
            reservedSeatCarriage.setTrain(train);
            reservedSeatCarriage.setSeats(createSeats(20, reservedSeatCarriage));
            carriages.add(reservedSeatCarriage);
        }

        Carriage restaurantCarriage = new Carriage();
        restaurantCarriage.setType("Ресторан");
        restaurantCarriage.setNumberCarriage(1);
        restaurantCarriage.setTrain(train);
        carriages.add(restaurantCarriage);

        // Сохраняем вагоны в базе данных
        for (Carriage carriage : carriages) {
            carriageService.createCarriage(carriage);
        }

        return ResponseEntity.ok("Поезд успешно создан");
    }

    private List<Seat> createSeats(int numberOfSeats, Carriage carriage) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i + 1);
            seat.setPrice(1000); // Установите цену по умолчанию, если необходимо
            seat.setCarriage(carriage);
            seat.setIsOccupied(false); // Изначально все места свободны
            seats.add(seat);
        }
        return seats;
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

            // Получаем маршрут по идентификатору, если указан
            if (request.getRouteId() != null) {
                Route route = routeService.getRouteById(request.getRouteId());
                if (route != null) {
                    existingTrain.setRoute(route);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Маршрут с id " + request.getRouteId() + " не найден");
                }
            }

            // Обновляем вагоны, если они указаны в запросе
            if (request.getCarriages() != null && !request.getCarriages().isEmpty()) {
                for (CarriageRequest carriageRequest : request.getCarriages()) {
                    Carriage existingCarriage = carriageService.getCarriageById(carriageRequest.getId());
                    if (existingCarriage != null) {
                        // Обновляем данные вагона
                        existingCarriage.setType(carriageRequest.getType());
                        existingCarriage.setNumberCarriage(carriageRequest.getNumberCarriage());
                        // Обновляем места в вагоне, если они указаны
                        if (carriageRequest.getSeats() != null && !carriageRequest.getSeats().isEmpty()) {
                            List<Seat> updatedSeats = new ArrayList<>();
                            for (SeatRequest seatRequest : carriageRequest.getSeats()) {
                                Seat existingSeat = seatService.getSeatByNumberAndCarriageId(seatRequest.getSeatNumber(), carriageRequest.getId());
                                if (existingSeat != null) {
                                    // Обновляем данные места
                                    existingSeat.setSeatNumber(seatRequest.getSeatNumber());
                                    existingSeat.setPrice((int) seatRequest.getPrice());
                                    existingSeat.setIsOccupied(seatRequest.isOccupied());
                                    updatedSeats.add(existingSeat);
                                }
                            }
                            existingCarriage.setSeats(updatedSeats);
                        }
                        carriageService.updateCarriage(existingCarriage);
                    }
                }
            }

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
