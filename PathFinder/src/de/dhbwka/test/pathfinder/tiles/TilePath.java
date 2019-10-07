package de.dhbwka.test.pathfinder.tiles;

import de.dhbwka.test.pathfinder.*;

public class TilePath extends Tile {
    
    
    public TilePath(int row, int column, boolean horizontal) {
        super(row, column, TileImages.path, horizontal ? Rotation.QUARTER : Rotation.NONE);
    }
    
    public TilePath(int row, int column) {
        super(row, column, TileImages.path);
    }
    
    @Override
    public Side[] getOutputSides() {
        return isActive() ? (isHorizontal() ? new Side[]{Side.EAST, Side.WEST} : new Side[]{Side.NORTH, Side.SOUTH}) : new Side[0];
    }
    
    
    @Override
    public void inputOnSide(Side side) {
        active = active || (isHorizontal() ? (side == Side.EAST || side == Side.WEST) : (side == Side.NORTH || side == Side.SOUTH));
    }
    
    @Override
    public void resetInputSides() {
        active = false;
    }
    
    @Override
    public boolean isRequired() {
        return false;
    }
    
    private boolean isHorizontal() {
        return rotation == Rotation.NONE || rotation == Rotation.HALF;
    }
}
