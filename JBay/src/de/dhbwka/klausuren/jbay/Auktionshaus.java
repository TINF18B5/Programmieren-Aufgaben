package de.dhbwka.klausuren.jbay;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Auktionshaus {
    
    private final List<Auktion> auctions;
    private final List<BieterTerminal> terminals;
    
    public Auktionshaus() {
        this.auctions = new LinkedList<>();
        terminals = new LinkedList<>();
    }
    
    public void addAuktion(Auktion a) {
        this.auctions.add(a);
    }
    
    public void removeAuktion(Auktion a) {
        this.auctions.remove(a);
    }
    
    public List<Auktion> getAuktionen() {
        return this.auctions;
    }
    
    
    public void updateTerminals() {
        this.terminals.forEach(BieterTerminal::updateTerminal);
    }
    
    public void register(BieterTerminal terminal) {
        this.terminals.add(terminal);
    }
    
    public void unregister(BieterTerminal terminal) {
        this.terminals.remove(terminal);
    }
    
    public void gebotAbgegeben(Auktion auktion, Gebot gebot) {
        updateTerminals();
        try(final BufferedWriter writer = Files.newBufferedWriter(Paths.get("auktionen.txt"), Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
         writer.write(String.format(Locale.ENGLISH, "[%s] Gebot von %s für %s: %f Euro.", Calendar.getInstance(), gebot.getBieter().getFullName(), auktion.getWare().getTitle(), gebot.getMax()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
