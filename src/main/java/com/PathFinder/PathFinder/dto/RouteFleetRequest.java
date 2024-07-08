package com.PathFinder.PathFinder.dto;

import com.PathFinder.PathFinder.models.Route;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class RouteFleetRequest {

    @Getter @Setter
    private int fleetId;

    @Getter @Setter
    private List<Route> routes;

}
