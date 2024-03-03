package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Route;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findRouteByRouteName(String routeName);
    List<Route> findRoutesByRouteName(String routeName);

    @Query("SELECT DISTINCT r FROM Route r JOIN r.stationSchedules ss1 JOIN r.stationSchedules ss2 WHERE ss1.station.id = :stationId1 AND ss2.station.id = :stationId2")
    List<Route> findRoutesByStations(@Param("stationId1") Long stationId1, @Param("stationId2") Long stationId2);
}
