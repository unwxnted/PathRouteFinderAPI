package com.PathFinder.PathFinder.repositories;

import com.PathFinder.PathFinder.models.Fleet;
import com.PathFinder.PathFinder.models.User;
import com.PathFinder.PathFinder.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface FleetRepository extends JpaRepository<Fleet, Integer> {
    List<Fleet> findByUser(User user);
}
