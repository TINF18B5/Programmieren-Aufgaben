package de.dhbw.ka.testklausur.zoo;

import java.io.IOException;

public class ZooFileException extends Exception {
    public ZooFileException(IOException e) {
        super("Fehler: Konnte nicht auf die Datei zugreifen!", e);
    }
}
