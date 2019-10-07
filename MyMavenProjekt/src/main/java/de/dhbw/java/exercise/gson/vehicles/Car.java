package de.dhbw.java.exercise.gson.vehicles;

public class Car extends Vehicle {

    public Car() {
        super(4);
    }
    public Car(double startSpeed) {
        this(140.0D, startSpeed);
    }

    public Car(double maxSpeed, double startSpeed) {
        super(4, maxSpeed, startSpeed);
    }
}
