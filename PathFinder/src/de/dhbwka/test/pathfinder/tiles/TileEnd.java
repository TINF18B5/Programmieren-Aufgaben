package de.dhbwka.test.pathfinder.tiles;

import de.dhbwka.test.pathfinder.*;

public class TileEnd extends Tile {
    
    public TileEnd(int row, int column) {
        super(row, column, TileImages.end);
    }
    
    @Override
    public Side[] getOutputSides() {
        return new Side[0];
    }
    
    @Override
    public void inputOnSide(Side side) {
        if(side == Side.NORTH.rotate(this.rotation))
            this.active = true;
    }
    
    @Override
    public void resetInputSides() {
        this.active = false;
    }
    
    @Override
    public boolean isRequired() {
        return true;
    }
}
