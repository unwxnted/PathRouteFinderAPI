package com.PathFinder.PathFinder.repositories;

import com.PathFinder.PathFinder.models.Fleet;
import com.PathFinder.PathFinder.models.User;
import com.PathFinder.PathFinder.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findAllByFleet(Fleet fleet);
}
