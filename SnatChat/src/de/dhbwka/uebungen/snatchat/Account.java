package de.dhbwka.uebungen.snatchat;

import java.awt.*;
import java.util.*;

public class Account {
    private static final Random random = new Random();
    private final String name;
    private State state;
    private final Color color;
    
    public Account(String name) {
        this.name = name;
        this.state = State.AVAILABLE;
    
        color = new Color(random.nextInt(201), random.nextInt(201), random.nextInt(201), 255);
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String getName() {
        return name;
    }
}
