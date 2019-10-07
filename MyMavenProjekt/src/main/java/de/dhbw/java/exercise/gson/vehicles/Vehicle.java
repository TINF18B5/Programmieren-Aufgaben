package de.dhbw.java.exercise.gson.vehicles;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public abstract class Vehicle {
    private final int wheels;
    private final double maxSpeed;

    private double positionKm;
    private double currentSpeed;

    public Vehicle(final int wheels) {
        this(wheels, 0.0D, 0.0D);
    }

    public Vehicle(final int wheels, final double maxSpeed, final double currentSpeed) {
        this.wheels = wheels;
        this.maxSpeed = maxSpeed;
        this.currentSpeed = Math.min(currentSpeed, maxSpeed);
    }


    public int getWheels() {
        return wheels;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getPositionKm() {
        return positionKm;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(final double currentSpeed) {
        this.currentSpeed = Math.min(currentSpeed, getMaxSpeed());
    }

    public void drive(final double minutes) {
        this.driveHours(minutes / 60.0D);
    }

    public void driveHours(final double hours) {
        this.positionKm += currentSpeed * hours;
    }

    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner(", ");
        sj.add("Vehicle type " + this.getClass().getSimpleName());
        sj.add(this.getWheels() + " wheels");
        sj.add("current speed: " + this.getCurrentSpeed() + " km/h");
        sj.add("maximum speed: " + this.getMaxSpeed() + " km/h");
        sj.add("current position: " + this.getPositionKm());
        return sj.toString();
    }
}
