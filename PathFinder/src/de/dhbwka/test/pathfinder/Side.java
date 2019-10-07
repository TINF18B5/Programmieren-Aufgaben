package de.dhbwka.test.pathfinder;

import de.dhbwka.test.pathfinder.tiles.*;

public enum Side {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);
    
    private final int columnOffset;
    private final int rowOffset;
    
    Side(int columnOffset, int rowOffset) {
        
        this.columnOffset = columnOffset;
        this.rowOffset = rowOffset;
    }
    
    public int getColumnOffset() {
        return columnOffset;
    }
    
    public int getRowOffset() {
        return rowOffset;
    }
    
    public Side getOpposite() {
        switch(this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
        }
        
        System.err.println("Nani?");
        return NORTH;
    }
    
    public Side rotate(Rotation rotation) {
        final Side[] values = values();
        return values[(this.ordinal() + rotation.ordinal()) % values.length];
    }
}
