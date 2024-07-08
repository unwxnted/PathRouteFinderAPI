CREATE DATABASE PathFinder;

USE PathFinder;

CREATE TABLE user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE fleets(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE vehicles(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    lat FLOAT,
    lng FLOAT,
    fleet_id INT NOT NULL,
    FOREIGN KEY (fleet_id) REFERENCES fleets(id) ON DELETE CASCADE
);
