package com.PathFinder.PathFinder.controllers;

import com.PathFinder.PathFinder.models.Route;
import com.PathFinder.PathFinder.dto.RouteRequest;
import com.PathFinder.PathFinder.dto.VehicleRequest;
import com.PathFinder.PathFinder.models.*;
import com.PathFinder.PathFinder.security.JwtTokenProvider;
import com.PathFinder.PathFinder.services.UserService;
import com.PathFinder.PathFinder.services.VehicleService;
import com.PathFinder.PathFinder.services.RouteUtilService;
import com.PathFinder.PathFinder.util.RouteUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
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
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    RouteUtil routeUtil;

    @GetMapping("/all")
    public List<Vehicle> getVehicles(HttpServletRequest req){
        String token = jwtTokenProvider.resolveToken(req);
        String username = jwtTokenProvider.getUserNameFromToken(token);
        User user = userService.getByName(username);
        List<Vehicle> response = vehicleService.getAllVehiclesByUserId(user.getId());
        response.forEach(v -> {
            v.getFleet().getUser().setPassword("");
        });
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id, HttpServletRequest req){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        try{
            Vehicle vehicle = vehicleService.getById(id, user.getId());
            vehicle.getFleet().getUser().setPassword("");
            return ResponseEntity.ok(vehicle);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/route")
    public ResponseEntity<?> calculateRoute(@RequestBody RouteRequest routeRequest, HttpServletRequest req){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        try{
            Vehicle vehicle = vehicleService.getById(routeRequest.getVehicleId(), user.getId());
            return ResponseEntity.ok(routeUtil.calculate(routeRequest.getRoutes(), vehicle));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> postVehicle(@RequestBody VehicleRequest vehicleRequest){
        Vehicle savedVehicle = vehicleService.postVehicle(vehicleRequest);
        return ResponseEntity.ok(savedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteVehicle(@PathVariable int id, HttpServletRequest req){
        User user = userService.getByName(jwtTokenProvider.getUserNameFromToken(jwtTokenProvider.resolveToken(req)));
        try{
            vehicleService.deleteById(id, user.getId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok("Deleted Successfully");
    }

}
