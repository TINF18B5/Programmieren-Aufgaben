package de.dhbwka.klausuren.stadtlandfluss;

public class Player {
    
    private final String name;
    private int points;
    
    public Player(String name) {
        this.name = name;
        this.points = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public int addPoints(int points) {
        return this.points += points;
    }
}
