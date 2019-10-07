package de.dhbwka.test.pathfinder.tiles;

import de.dhbwka.test.pathfinder.*;

public class TileIntersection extends Tile {
    
    public TileIntersection(int row, int column) {
        super(row, column, TileImages.intersection);
    }
    
    @Override
    public Side[] getOutputSides() {
        return active ? new Side[] {Side.WEST.rotate(rotation), Side.NORTH.rotate(rotation), Side.EAST.rotate(rotation)} : new Side[0];
    }
    
    @Override
    public void inputOnSide(Side side) {
        //Three possible sides, so easier to check for the one side that isn't
        active = active || side != Side.SOUTH.rotate(rotation);
    }
    
    @Override
    public void resetInputSides() {
        active = false;
    }
}
