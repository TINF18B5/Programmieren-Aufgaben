package de.dhbwka.test.pathfinder;

import java.util.*;

public class Game {
    
    private final List<Level> levels;
    private final PlayerWindow playerWindow;
    
    private int currentLevel = -1;
    
    public Game(List<Level> levels, String name) {
        this.levels = levels;
        this.playerWindow = new PlayerWindow(name, this);
        
        startRound();
    }
    
    public void startRound() {
        currentLevel++;
        playerWindow.startRound(levels.get(currentLevel));
    }
    
    
    public void finishedLevel() {
        if(currentLevel >= levels.size() - 1) {
            playerWindow.gameIsOver();
        } else {
            playerWindow.roundIsOver();
            startRound();
        }
    }
}
