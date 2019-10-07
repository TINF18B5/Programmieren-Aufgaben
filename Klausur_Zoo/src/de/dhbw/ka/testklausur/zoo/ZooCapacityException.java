package de.dhbw.ka.testklausur.zoo;

public class ZooCapacityException extends Exception {
    public ZooCapacityException () {
        super("Fehler: Zoo-Kapazität überschritten");
    }
}
