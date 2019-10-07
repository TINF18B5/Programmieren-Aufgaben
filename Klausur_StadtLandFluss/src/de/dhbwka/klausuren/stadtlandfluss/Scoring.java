package de.dhbwka.klausuren.stadtlandfluss;

public enum Scoring {
    NO_ANSWER(0), INCORRECT_ANSWER(0), CORRECT(5), UNIQUE(10), SINGLE_UNIQUE(20);
    
    private final int points;
    
    private Scoring(int points) {
        this.points = points;
    }
    
    public int getPoints() {
        return points;
    }
}
