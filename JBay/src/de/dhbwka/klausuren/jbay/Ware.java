package de.dhbwka.klausuren.jbay;

public class Ware {
    private final String title;
    private final String desc;
    
    public Ware(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
    
    public String getTitle() {
        return title;
    }
}
