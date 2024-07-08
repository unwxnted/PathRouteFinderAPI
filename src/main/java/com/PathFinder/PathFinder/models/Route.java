package com.PathFinder.PathFinder.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Route {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private float lat;

    @Getter @Setter
    private float lng;

    @Getter @Setter
    private float dist;

    @Getter @Setter
    private float distToNext;

}
