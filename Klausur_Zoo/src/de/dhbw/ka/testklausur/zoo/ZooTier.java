package de.dhbw.ka.testklausur.zoo;

import java.util.Objects;

public class ZooTier {
    private final String name, species, food;

    public ZooTier(String species, String name, String food) {
        this.name = name;
        this.species = species;
        this.food = food;
    }

    public void fuettern(String futter) {
        if(Objects.equals(food, futter)) {
            System.out.printf("%s frisst %s%n", this, futter);
        } else {
            System.out.printf("%s verschmäht %s%n", this, futter);
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, species);
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }
}
