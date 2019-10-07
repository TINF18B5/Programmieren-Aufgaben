package de.dhbwka.ppe.soemem;

import java.util.*;

public class MemoryGame {
    
    private final int rows;
    private final int cols;
    private final List<Player> players;
    private final List<MemoryImages.MemoryImage> selectedImages;
    
    private int currentPlayer;
    
    public MemoryGame(List<Player> players, List<MemoryImages.MemoryImage> availableImages, int rows, int cols) throws MemoryException{
        this.cols = cols;
        this.rows = rows;
        this.currentPlayer = -1;
        
        if(players.size() < 2) {
            throw new MemoryException("At least two players required");
        }
        
        final int requiredImages = (rows * cols) / 2;
        if(requiredImages > availableImages.size()) {
            throw new MemoryException("Too few images available");
        }
        
        Collections.shuffle(availableImages);
        this.selectedImages = availableImages.subList(0, requiredImages);
        this.players = players;
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }
    
    public boolean isBlankRequired() {
        return (rows * cols) % 2 == 1;
    }
    
    public void nextPlayer() {
        if(currentPlayer != -1)
            getCurrentPlayer().setStatus(PlayerStatus.WAITING);
        currentPlayer = (currentPlayer + 1) % players.size();
        getCurrentPlayer().setStatus(PlayerStatus.ACTIVE);
    }
    
    public List<Player> getPlayers() {
        return players;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public List<MemoryImages.MemoryImage> getSelectedImages() {
        return selectedImages;
    }
}
