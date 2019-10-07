package de.dhbwka.test.pathfinder.tiles;

import java.util.*;

public enum Rotation {
    NONE, QUARTER, HALF, THREE_QUARTERS;
    
    private static final Random random = new Random();
    
    public static Rotation getRandom() {
        final Rotation[] values = values();
        return values[random.nextInt(values.length)];
    }
    
    public Rotation next() {
        final Rotation[] values = values();
        return values[(this.ordinal() + 1) % values.length];
    }
}
