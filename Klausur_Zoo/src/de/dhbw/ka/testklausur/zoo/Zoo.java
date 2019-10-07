package de.dhbw.ka.testklausur.zoo;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class Zoo {
    private final ZooTier[] animals;
    private int currentAnimals;

    public Zoo() {
        this(5);
    }

    public Zoo(int max) {
        this.animals = new ZooTier[max];
        this.currentAnimals = 0;
    }

    public static void main(String[] args) {
        Zoo z = new Zoo();


        try {
            z.addAnimal(new Raubtier("Tiger", "Fred"));
            z.addAnimal(new Raubtier("Tiger", "Lisa"));
            z.addAnimal(new Raubtier("Löwe", "Simba"));
            z.addAnimal(new Singvogel("Kleiber", "Hansi"));
            z.addAnimal(new Singvogel("Schneeammer", "Sina"));
            z.addAnimal(new Singvogel("Zaunkoenig", "Henry"));
        } catch (ZooCapacityException e) {
            e.printStackTrace();
        }


        try {
            z.saveToFile("zootiere.txt");
            System.out.println("Tiere in Datei Gespeichert!");
        } catch (ZooFileException e) {
            e.printStackTrace();
        }

        z.fuettern("Körner");


        for (String name : new String[]{"Fred", "Kimba", "Henry", "Lotte"}) {
            System.out.printf("Gibt es '%s' im Zoo? ", name);
            System.out.println(z.existsAnimal(name) ? "ja" : "nein");
        }
    }

    public void addAnimal(ZooTier tier) throws ZooCapacityException {
        if (currentAnimals >= animals.length) {
            throw new ZooCapacityException();
        }

        animals[currentAnimals++] = tier;
        System.out.println(tier + " zum Zoo hinzugefügt.");
    }

    public ZooTier[] getAnimals() {
        return animals;
    }

    public boolean existsAnimal(String name) {
        for (ZooTier zooTier : animals) {
            if (Objects.equals(zooTier.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    public void fuettern(String futter) {
        for (ZooTier animal : animals) {
            if (animal != null)
                animal.fuettern(futter);
        }
    }

    public void saveToFile(String fileName) throws ZooFileException {
        try (final PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (ZooTier animal : animals) {
                if (animal == null) continue;

                writer.printf("%s;%s;%s%n", animal.getSpecies(), animal.getName(), animal.getClass().getSimpleName());
            }
        } catch (IOException e) {
            throw new ZooFileException(e);
        }
    }
}
