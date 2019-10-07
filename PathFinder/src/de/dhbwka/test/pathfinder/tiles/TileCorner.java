package de.dhbwka.test.pathfinder.tiles;

import de.dhbwka.test.pathfinder.*;

public class TileCorner extends Tile {
    
    public TileCorner(int row, int column) {
        super(row, column, TileImages.corner);
    }
    
    
    @Override
    public Side[] getOutputSides() {
        return active ? new Side[]{Side.NORTH.rotate(rotation), Side.EAST.rotate(rotation)} : new Side[0];
    }
    
    
    @Override
    public void inputOnSide(Side side) {
        active = active || side == Side.NORTH.rotate(rotation) || side == Side.EAST.rotate(rotation);
        
    }
    
    @Override
    public void resetInputSides() {
        active = false;
    }
    
}
