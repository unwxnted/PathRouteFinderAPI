package com.PathFinder.PathFinder.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="fleets")
@ToString
public class Fleet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name="id")
    private int id;

    @Getter @Setter @Column(name="name")
    private String name;

    @Getter @Setter @ManyToOne @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "fleet", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

}
