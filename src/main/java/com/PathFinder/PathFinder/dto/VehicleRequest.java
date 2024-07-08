package com.PathFinder.PathFinder.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class VehicleRequest {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private float lat;

    @Getter @Setter
    private float lng;

    @Getter @Setter
    private int fleetId;

}
