package tuleuov.space.railway.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.StationSchedule;
import tuleuov.space.railway.entity.Ticket;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.repository.RouteRepository;
import tuleuov.space.railway.repository.TicketRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final StationService stationService;

    @Autowired
    public RouteService(RouteRepository routeRepository, StationService stationService) {
        this.routeRepository = routeRepository;
        this.stationService = stationService;
    }
    public Route initializeRoute(Route route) {
        Hibernate.initialize(route.getStationSchedules());
        return route;
    }
    public Route createRoute(Route route){
        return routeRepository.save(route);
    }

    public Route getRouteById(Long routeId) {
        return routeRepository.findById(routeId)
                .orElse(null);
    }

    public Route getRouteByRouteName(String routeName) {
        return routeRepository.findRouteByRouteName(routeName)
                .orElse(null);
    }

    public void deleteRouteById(Long routeId) {
        routeRepository.deleteById(routeId);
    }

    public Route updateRoute(Route route) {
        if (route.getId() == null) {
            throw new IllegalArgumentException("Route must have an ID for update.");
        }

        return routeRepository.save(route);
    }

    public List<Route> getAll() {
        return routeRepository.findAll();
    }
    public List<Route> getRoutesByRouteName(String routeName) {
        return routeRepository.findRoutesByRouteName(routeName);
    }

//  Поиск маршрута по 2 станциям
public List<Route> findRoutesByStationNames(String stationName1, String stationName2) {
    Long stationId1 = stationService.findStationIdByName(stationName1);
    Long stationId2 = stationService.findStationIdByName(stationName2);
    List<Route> routes = routeRepository.findRoutesByStations(stationId1, stationId2);
    List<Route> filteredRoutes = new ArrayList<>();

    // Проверяем каждый маршрут из результата запроса
    for (Route route : routes) {
        List<StationSchedule> schedules = new ArrayList<>(route.getStationSchedules());

        // Находим индексы станций с указанными именами
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < schedules.size(); i++) {
            StationSchedule schedule = schedules.get(i);
            if (schedule.getStation().getStationName().equals(stationName1)) {
                index1 = i;
            } else if (schedule.getStation().getStationName().equals(stationName2)) {
                index2 = i;
            }
        }

        // Проверяем, что станция 2 следует после станции 1 в маршруте
        if (index1 != -1 && index2 != -1 && index2 > index1) {
            filteredRoutes.add(route);
        }
    }

    return filteredRoutes;
}

}

