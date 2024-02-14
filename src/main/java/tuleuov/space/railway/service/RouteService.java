package tuleuov.space.railway.service;

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
    public Route createRoute(Route route){
        return routeRepository.save(route);
    }

    public Route getRouteById(Long routeId) {
        Route route = routeRepository.getById(routeId);
        return route;
    }
    public Route getRouteByRouteName(String routeName) {
        Optional<Route> optionalRoute = routeRepository.findRouteByRouteName(routeName);
        return optionalRoute.orElse(null);
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
}
