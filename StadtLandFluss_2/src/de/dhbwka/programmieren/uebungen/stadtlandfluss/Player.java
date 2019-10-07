package de.dhbwka.programmieren.uebungen.stadtlandfluss;

public class Player {
    private final String name;
    private int score;
    
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }
    
    public int getScore() {
        return score;
    }
    
    public int addScore(int score) {
        return this.score += score;
    }
    
    public String getName() {
        return name;
    }
}
