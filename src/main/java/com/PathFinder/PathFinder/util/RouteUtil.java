package com.PathFinder.PathFinder.util;

import com.PathFinder.PathFinder.controllers.VehicleController;
import com.PathFinder.PathFinder.dto.RouteFleetRequest;
import com.PathFinder.PathFinder.dto.RouteFleetResponse;
import com.PathFinder.PathFinder.models.Route;
import com.PathFinder.PathFinder.models.Vehicle;
import com.PathFinder.PathFinder.services.FleetService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ToString
@Component
public class RouteUtil {

    @Getter @Setter
    private List<Route> path;

    private float distToNext;

    @Autowired
    FleetService fleetService;

    public List<Route> calculate(List<Route> routes, Vehicle vehicle){

        routes.forEach(r -> {
            double latDiff = r.getLat() - vehicle.getLat();
            double lngDiff = r.getLng() - vehicle.getLng();
            double dist = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lngDiff, 2));
            r.setDist((float) dist);
        });

        Collections.sort(routes, new Comparator<Route>() {
            @Override
            public int compare(Route r1, Route r2) {
                return Float.compare(r1.getDist(), r2.getDist());
            }
        });

        for(int i = 0;i < routes.size(); ++i){
            if (i == 0) {
                distToNext = routes.get(i).getDist();
            } else {
                double latDiff = routes.get(i).getLat() - routes.get(i - 1).getLat();
                double lngDiff = routes.get(i).getLng() - routes.get(i - 1).getLng();
                distToNext = (float) Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lngDiff, 2));
            }

            routes.get(i).setDistToNext(distToNext);
        }

        return routes;
    }

    public List<RouteFleetResponse> calculateFleet(RouteFleetRequest routeFleetRequest, int userId){

        List<RouteFleetResponse> routeFleetResponses = new ArrayList<>();

        List<Vehicle> vehicles = new ArrayList<>();

        try{
            vehicles = fleetService.getVehiclesByFleetId(routeFleetRequest.getFleetId(), userId);
        }catch (Exception e){
            return routeFleetResponses;
        }

        vehicles.forEach(v -> {

            RouteFleetResponse routeFleetResponse = new RouteFleetResponse();

            routeFleetResponse.setVehicleId(v.getId());

            List<Route> temp = new ArrayList<>(routeFleetRequest.getRoutes());

            routeFleetResponse.setRoutes(calculate(temp, v));

            routeFleetResponses.add(routeFleetResponse);

        });

        return routeFleetResponses;
    }
}
