package tuleuov.space.railway.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.entity.Route;
import tuleuov.space.railway.entity.Ticket;
import tuleuov.space.railway.entity.Train;
import tuleuov.space.railway.repository.RouteRepository;
import tuleuov.space.railway.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
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
    public List<Route> findRoutesByStations(Long stationId1, Long stationId2) {
        return routeRepository.findRoutesByStations(stationId1, stationId2);
    }
}

