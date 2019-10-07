package de.dhbw.java.exercise.gson.vehicles;

@SuppressWarnings("WeakerAccess")
public class Ambulance extends Car {
    private boolean signalOn;

    public Ambulance(double startSpeed, boolean signalOn) {
        super(startSpeed);
        this.signalOn = signalOn;
    }

    public Ambulance(boolean signalOn) {
        super();
        this.signalOn = signalOn;
    }


    public boolean isSignalOn() {
        return signalOn;
    }

    public void setSignalOn(boolean signalOn) {
        this.signalOn = signalOn;
    }

    @Override
    public String toString() {
        return super.toString() + ", Signal " + (this.isSignalOn() ? "on" : "off");
    }
}
