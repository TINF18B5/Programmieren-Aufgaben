package de.dhbwka.klausuren.stadtlandfluss;

public enum ColumnType {
    CITY("Stadt"), COUNTRY("Land"), RIVER("Fluss"), PROFESSION("Beruf"), ANIMAL("Tier"), NAME("Vorname"), SPORT("Sportart"), FOOD("Lebensmittel"), BEVERAGE("Getränk"), GAME("Spiel");
    
    private final String title;
    
    ColumnType(String title) {
        
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
}
