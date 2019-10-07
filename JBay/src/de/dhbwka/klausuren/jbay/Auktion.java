package de.dhbwka.klausuren.jbay;

import java.util.*;

public class Auktion {
    
    private static final double increment = 1.0D;
    
    private final Ware ware;
    private final Calendar end;
    
    private Gebot maxGebot;
    private double preis;
    
    
    
    public Auktion(Ware ware, int dauer) {
        this.ware = ware;
        this.maxGebot = null;
        this.preis = 0.0D;
        this.end = Calendar.getInstance();
        this.end.add(Calendar.MINUTE, dauer);
    }
    
    public boolean gebotAbgegeben(Gebot g) {
        
        
        if(g.getMax() < preis + increment) {
            return false;
        }
        
        if(maxGebot == null) {
            maxGebot = g;
            preis = increment;
            return true;
        }
        
        if(maxGebot.getBieter() == g.getBieter()) {
            maxGebot = g;
            return true;
        }
        
        if(g.getMax() <= maxGebot.getMax()) {
            preis = Math.min(g.getMax() + increment, maxGebot.getMax());
            return false;
        }
        
        preis = Math.min(g.getMax(), maxGebot.getMax() + increment);
        maxGebot = g;
        return true;
    }
    
    public Ware getWare() {
        return ware;
    }
    
    public Calendar getEnd() {
        return end;
    }
    
    public Gebot getMaxGebot() {
        return maxGebot;
    }
    
    public double getPreis() {
        return preis;
    }
    
    public boolean isOver() {
        return Calendar.getInstance().after(end);
    }
    
    public double getMinBiddingValue() {
        return preis + increment;
    }
}
