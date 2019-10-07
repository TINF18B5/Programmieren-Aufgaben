package de.dhbwka.test.pathfinder.tiles;

import de.dhbwka.test.pathfinder.*;

import javax.swing.*;
import java.util.*;

public abstract class Tile {
    
    private final int row;
    private final int column;
    private final TileImages.TileImage image;
    protected Rotation rotation;
    protected boolean active;
    
    protected Tile(int row, int column, TileImages.TileImage image) {
        this(row, column, image, Rotation.getRandom());
    }
    
    protected Tile(int row, int column, TileImages.TileImage image, Rotation rotation) {
        this.row = row;
        this.column = column;
        this.image = image;
        this.rotation = rotation;
        active = false;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
    
    public boolean canBeRotated() {
        return true;
    }
    
    public abstract Side[] getOutputSides();
    
    public void rotate() {
        this.rotation = this.rotation.next();
    }
    
    public abstract void inputOnSide(Side side);
    
    public abstract void resetInputSides();
    
    public boolean isActive() {
        return active;
    }
    
    public boolean isRequired() {
        return false;
    }
    
    public ImageIcon getIcon() {
        return image.getImageIcon(rotation, isActive());
    }
}
