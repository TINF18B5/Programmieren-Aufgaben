package de.dhbwka.klausuren.jbay;

public class Gebot {
    private final Bieter bieter;
    private final double max;
    
    public Gebot(Bieter bieter, double max) {
        this.bieter = bieter;
        this.max = max;
    }
    
    public Bieter getBieter() {
        return bieter;
    }
    
    public double getMax() {
        return max;
    }
}
