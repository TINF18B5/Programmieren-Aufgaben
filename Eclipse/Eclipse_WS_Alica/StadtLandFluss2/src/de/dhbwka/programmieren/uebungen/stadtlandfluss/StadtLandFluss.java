package de.dhbwka.programmieren.uebungen.stadtlandfluss;

public class StadtLandFluss {
    
    public static void main(String[] args) {
        final Game slf = new Game(4, 6, 60);
        slf.register(new Sheet(new Player("Otto"), slf));
        slf.register(new Sheet(new Player("Anna"), slf));
    }
}
