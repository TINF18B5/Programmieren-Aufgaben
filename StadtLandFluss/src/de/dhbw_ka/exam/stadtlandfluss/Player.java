package de.dhbw_ka.exam.stadtlandfluss;

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
    
    public int getPoints() {
        return points;
    }
    
    public int addPoints(int points) {
        return this.points += points;
    }
}
