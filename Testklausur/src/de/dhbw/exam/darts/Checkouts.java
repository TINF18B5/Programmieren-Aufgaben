package de.dhbw.exam.darts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a listing of possible "checkouts" that is throw combinations to finish the game.
 */
public class Checkouts {
    /**
     * Stores the checkouts so that we don't have to load the file multiple times.
     */
    private final String[] checkouts;

    public Checkouts() {
        String[] checkOuts1;
        try(final BufferedReader reader = new BufferedReader(new FileReader(new File("files/checkouts.txt")))) {
            final List<String> checkouts = new ArrayList<>();
            while (reader.ready()) {
                checkouts.add(reader.readLine());
            }
            checkOuts1 = checkouts.toArray(new String[0]);
        } catch (IOException e) {
            //In case we could not read the file, just ignore them.
            System.err.println("Could not read checkouts file: " + e.getMessage());
            checkOuts1 =  new String[0];
        }
        this.checkouts = checkOuts1;
    }

    /**
     * Gets a possible checkout for the given reminingPoints
     * @param remainingPoints the points to be reached
     * @return A possible checkout (e.g. "T20 T14 D20") or "-" if none if found.
     */
    public String getCheckout(int remainingPoints) {
        if(remainingPoints <= checkouts.length) {
            return checkouts[remainingPoints - 1];
        }
        return "-";
    }
}
