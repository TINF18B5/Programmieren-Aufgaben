package de.dhbwka.klausuren.jbay;

import java.util.*;

public class Bieter {
    
    private final String vorname;
    private final String nachname;
    
    
    public Bieter(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }
    
    String getFullName() {
        return String.format(Locale.ENGLISH, "%s %s", vorname, nachname);
    }
}
