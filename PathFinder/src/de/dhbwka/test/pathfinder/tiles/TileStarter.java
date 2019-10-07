package de.dhbwka.test.pathfinder.tiles;

import de.dhbwka.test.pathfinder.*;

public class TileStarter extends Tile {
    
    
    public TileStarter(int row, int column) {
        super(row, column, TileImages.starter);
    }
    
    @Override
    public Side[] getOutputSides() {
        return new Side[]{Side.NORTH.rotate(this.rotation)};
    }
    
    @Override
    public void inputOnSide(Side side) {
    }
    
    @Override
    public void resetInputSides() {
    }
    
    @Override
    public boolean isActive() {
        return true;
    }
}
