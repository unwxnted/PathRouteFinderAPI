package com.PathFinder.PathFinder.services;

import com.PathFinder.PathFinder.dto.VehicleRequest;
import com.PathFinder.PathFinder.models.Fleet;
import com.PathFinder.PathFinder.models.User;
import com.PathFinder.PathFinder.models.Vehicle;
import com.PathFinder.PathFinder.repositories.FleetRepository;
import com.PathFinder.PathFinder.repositories.UserRepository;
import com.PathFinder.PathFinder.repositories.VehicleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    FleetRepository fleetRepository;

    @Autowired
    EntityManager entityManager;

    public List<Vehicle> getAllVehiclesByUserId(int id){
        String sql = "SELECT v FROM Vehicle v JOIN v.fleet f JOIN f.user u WHERE u.id = :userId";

        return entityManager.createQuery(sql)
                .setParameter("userId", id)
                .getResultList();
    }

    public Vehicle getById(int id, int userId) throws AccessDeniedException{
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Vehicle Not Found"));

        if(vehicle.getFleet().getUser().getId() != userId) throw new AccessDeniedException("Access Denied");

        return vehicle;
    }

    public Vehicle postVehicle(VehicleRequest vehicleRequest){
        Fleet fleet = fleetRepository.findById(vehicleRequest.getFleetId())
                .orElseThrow(() -> new IllegalArgumentException("Fleet Not Found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setName(vehicleRequest.getName());
        vehicle.setLat(vehicleRequest.getLat());
        vehicle.setLng(vehicleRequest.getLng());
        vehicle.setFleet(fleet);
        return vehicleRepository.save(vehicle);
    }

    public void deleteById(int id, int userId) throws AccessDeniedException{

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle Not Found"));

        if(vehicle.getFleet().getUser().getId()!=userId) throw new AccessDeniedException("Access Denied");

        vehicleRepository.deleteById(id);
    }

}
