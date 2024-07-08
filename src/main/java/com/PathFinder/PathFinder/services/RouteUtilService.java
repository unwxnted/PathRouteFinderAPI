package com.PathFinder.PathFinder.services;

import com.PathFinder.PathFinder.util.RouteUtil;
import org.springframework.context.annotation.Bean;

public class RouteUtilService {

    @Bean
    public static RouteUtil routeUtil(){
        return new RouteUtil();
    }

}
