package de.dhbwka.ppe.soemem;

import java.util.*;

public class Player {
    
    private final String name;
    private int points;
    private PlayerStatus status;
    
    public Player(String name) {
        this.points = 0;
        this.name = name;
        this.status = PlayerStatus.WAITING;
    }
    
    public String getName() {
        return name;
    }
    
    public PlayerStatus getStatus() {
        return status;
    }
    
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void addPoint() {
        points++;
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s (%d)", name, points);
    }
}
