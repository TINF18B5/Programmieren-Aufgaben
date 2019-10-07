package de.dhbwka.test.pathfinder;

import de.dhbwka.test.pathfinder.tiles.*;

public class Level {
    
    private final int height, width;
    private final Tile[][] tiles;
    
    public Level(int height, int width, Tile[][] tiles) {
        this.height = height;
        this.width = width;
        this.tiles = tiles;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }
    
    public Tile getTileAt(int row, int column) {
        return tiles[row][column];
    }
}
