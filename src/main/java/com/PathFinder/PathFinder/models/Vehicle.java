package com.PathFinder.PathFinder.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="vehicles")
@ToString
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name="id")
    private int id;

    @Getter @Setter @Column(name="name")
    private String name;

    @Getter @Setter @Column(name="lat")
    private float lat;

    @Getter @Setter @Column(name="lng")
    private float lng;

    @Getter @Setter @ManyToOne @JoinColumn(name="fleet_id", nullable = false)
    private Fleet fleet;

}
