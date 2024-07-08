package com.PathFinder.PathFinder.dto;

import com.PathFinder.PathFinder.models.Route;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class RouteFleetResponse {

    @Getter @Setter
    private int vehicleId;

    @Getter @Setter
    private List<Route> routes;

}
