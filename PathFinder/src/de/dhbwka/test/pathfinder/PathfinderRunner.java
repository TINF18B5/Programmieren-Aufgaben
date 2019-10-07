package de.dhbwka.test.pathfinder;

import de.dhbwka.test.pathfinder.tiles.*;

import java.util.*;
import java.util.stream.*;

public class PathfinderRunner {
    
    public static void main(String[] args) {
        new Game(readLevels(), "PlayerName");
    }
    
    
    
    private static List<Level> readLevels() {
        final List<Level> out = new ArrayList<>(3);
        for(int i = 0; i < 3; i++) {
    
            final Tile[][] tiles = IntStream.range(0, 7).boxed().map(row -> IntStream.range(0, 7).mapToObj(col -> new TilePath(row, col)).toArray(Tile[]::new)).toArray(Tile[][]::new);
            int[] corners = new int[]{0, 3, 6};
            for(int row : corners) {
                for(int column : corners) {
                    tiles[row][column] = new TileCorner(row, column);
                }
            }
            tiles[0][3] = new TileIntersection(0, 3);
            tiles[0][6] = new TileEnd(0, 6);
            tiles[3][3] = new TileStarter(3, 3);
            
            out.add(new Level(7, 7, tiles));
        }
        return out;
    }
}
