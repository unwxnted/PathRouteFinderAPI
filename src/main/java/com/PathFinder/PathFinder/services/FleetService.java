package com.PathFinder.PathFinder.services;

import com.PathFinder.PathFinder.models.Fleet;
import com.PathFinder.PathFinder.models.User;
import com.PathFinder.PathFinder.models.Vehicle;
import com.PathFinder.PathFinder.repositories.FleetRepository;
import com.PathFinder.PathFinder.repositories.UserRepository;
import com.PathFinder.PathFinder.repositories.VehicleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class FleetService {

    @Autowired
    FleetRepository fleetRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    public List<Fleet> getAll(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        List<Fleet> fleets = fleetRepository.findByUser(user);
        fleets.forEach(f -> f.getUser().setPassword(""));
        return fleets;
    }

    public List<Vehicle> getVehiclesByFleetId(int id, int userId) throws AccessDeniedException{
        Fleet fleet =  fleetRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Fleet Not Found"));

        if(fleet.getUser().getId() != userId) throw new AccessDeniedException("Access Denied");

        return vehicleRepository.findAllByFleet(fleet);
    }

    public Fleet postFleet(Fleet fleet, int userId){
        fleet.setUser(userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User Not Found")));
        return fleetRepository.save(fleet);
    }

    public void deleteById(int id, int userId) throws AccessDeniedException{

        Fleet fleet = fleetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle Not Found"));

        if(fleet.getUser().getId()!=userId) throw new AccessDeniedException("Access Denied");

        fleetRepository.deleteById(id);
    }

}
