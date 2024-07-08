package com.PathFinder.PathFinder.controllers;

import com.PathFinder.PathFinder.dto.RouteFleetRequest;
import com.PathFinder.PathFinder.dto.RouteFleetResponse;
import com.PathFinder.PathFinder.dto.RouteRequest;
import com.PathFinder.PathFinder.models.Route;
import com.PathFinder.PathFinder.models.Fleet;
import com.PathFinder.PathFinder.models.User;
import com.PathFinder.PathFinder.models.Vehicle;
import com.PathFinder.PathFinder.security.JwtTokenProvider;
import com.PathFinder.PathFinder.services.FleetService;
import com.PathFinder.PathFinder.services.UserService;
import com.PathFinder.PathFinder.util.RouteUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

import static com.PathFinder.PathFinder.services.RouteUtilService.routeUtil;

@RestController
@RequestMapping("/api/fleets")
public class FleetController {

    @Autowired
    FleetService fleetService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    RouteUtil routeUtil;

    @GetMapping("/all")
    public List<Fleet> getAll(HttpServletRequest req){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        List<Fleet> fleets = fleetService.getAll(user.getId());
        fleets.forEach(f -> {
            f.getUser().setPassword("");
        });
        return fleets;
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<?> getVehiclesByFleetId(@PathVariable("id") int id, HttpServletRequest req){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        try{
            List<Vehicle> vehicles = fleetService.getVehiclesByFleetId(id, user.getId());
            vehicles.forEach(v -> {
                v.getFleet().getUser().setPassword("");
            });
            return  ResponseEntity.ok(vehicles);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @GetMapping("/route")
    public List<RouteFleetResponse> calculateRoutes(@RequestBody RouteFleetRequest routeRequest, HttpServletRequest req){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        return routeUtil.calculateFleet(routeRequest, user.getId());
    }

    @PostMapping("/")
    public Fleet postFleet(HttpServletRequest req, @RequestBody Fleet fleet){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        Fleet newFleet = fleetService.postFleet(fleet, user.getId());
        newFleet.getUser().setPassword("");
        return newFleet;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteFleet(@PathVariable int id, HttpServletRequest req){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        try{
            fleetService.deleteById(id, user.getId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok("Deleted Successfully");
    }

}
