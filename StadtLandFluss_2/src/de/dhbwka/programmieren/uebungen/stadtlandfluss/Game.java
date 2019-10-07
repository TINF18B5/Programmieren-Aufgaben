package de.dhbwka.programmieren.uebungen.stadtlandfluss;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Game {
    
    private static final Random rand = new Random();
    private static final String fileName = "validwords.txt";
    
    private final int min;
    private final int max;
    private final int seconds;
    private final Set<Sheet> sheets;
    private final Set<String> allowedWords;
    
    private boolean running = false;
    private char firstCharacter = '\0';
    private List<ColumnType> columns = null;
    
    public Game(int min, int max, int seconds) {
        this.min = Math.max(min, 3);
        this.max = Math.max(this.min, max);
        
        if(this.max > ColumnType.values().length) {
            throw new IllegalArgumentException("More max values than columns: " + max);
        }
        
        this.seconds = seconds;
        this.sheets = new HashSet<>();
        this.allowedWords = this.readAllowedWords();
    }
    
    private Set<String> readAllowedWords() {
        try(final BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return reader.lines().map(String::toLowerCase).collect(Collectors.toCollection(TreeSet::new));
        } catch(IOException e) {
            e.printStackTrace();
        }
    
        return new HashSet<>(0);
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void register(Sheet sheet) {
        this.sheets.add(sheet);
    }
    
    public void startGame() {
        if(this.isRunning())
            return;
        
        new Thread(() -> {
            int remaining = Game.this.seconds;
            while(Game.this.isRunning() && remaining > 0) {
                try {
                    Thread.sleep(1000, 0);
                } catch(InterruptedException ignored) {
                }
    
                for(Sheet sheet : Game.this.sheets) {
                    sheet.setRemainingSeconds(remaining);
                }
                
                remaining--;
            }
            
            if(Game.this.isRunning()) {
                Game.this.stopGame();
            }
            
        }).start();
        
        this.running = true;
        this.firstCharacter = this.createFirstCharacter();
        this.columns = this.createColumns();
        
        for(Sheet sheet : sheets) {
            sheet.gameStarted(columns, firstCharacter);
        }
    }
    
    public void stopGame() {
        if(!this.isRunning())
            return;
        
        this.running = false;
        for(Sheet sheet : sheets) {
            sheet.gameStopped();
        }
        
        this.evaluateScores();
        
        this.sheets.forEach(Sheet::updateScore);
    }
    
    private void evaluateScores() {
        for(ColumnType category : columns) {
            final HashMap<String, List<Sheet>> answers = new HashMap<>();
            
            //Erstmal alles in die Map
            for(Sheet spieler : sheets) {
                final String antwort = spieler.getInputFor(category).toLowerCase().trim();
                
                if(answers.containsKey(antwort)) {
                    final List<Sheet> sheets = answers.get(antwort);
                    sheets.add(spieler);
                } else {
                    //answers antwort spieler hinzu
                    final ArrayList<Sheet> spielerDieGeantwortetHaben = new ArrayList<>();
                    spielerDieGeantwortetHaben.add(spieler);
                    answers.put(antwort, spielerDieGeantwortetHaben);
                }
    
            }
            
            //Schmeiß falsche dinger raus
            for(String gegebeneAntwort : answers.keySet()) {
                if(!isValidAnswer(gegebeneAntwort, category)) {
                    answers.remove(gegebeneAntwort);
                }
            }
            
            //Punkte geben
            for(List<Sheet> spielerListe : answers.values()) {
                final int wieViele = spielerListe.size();
                if(wieViele > 1) {
                    for(Sheet spieler : spielerListe) {
                        spieler.getPlayer().addScore(5);
                        spieler.setScoreFor(category, 5);
                    }
                } else {
                    final Sheet sheet = spielerListe.get(0);
                    final int score = answers.size() == 1 ? 20 : 10;
                    sheet.setScoreFor(category, score);
                    sheet.getPlayer().addScore(score);
                }
            }
        }
    }
    
    private boolean isValidAnswer(String antwort, ColumnType category) {
        if(antwort.isEmpty() || antwort.charAt(0) != Character.toLowerCase(this.firstCharacter)) {
            return false;
        }
    
        if(this.allowedWords.contains(antwort)) {
            return true;
        }
    
        final int answer = JOptionPane.showConfirmDialog(null, String.format(Locale.ENGLISH, "Ist '%s' korrekt für die Kategorie '%s'?", antwort, category), "Option auswählen", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(answer != 0)
            return false;
        
        this.allowedWords.add(antwort);
        try(final BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            writer.write(antwort);
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    
        return true;
    }
    
    private char createFirstCharacter() {
        return (char) ('A' + rand.nextInt(26));
    }
    
    private List<ColumnType> createColumns() {
        final int columnCount = min + rand.nextInt(max - min);
        final List<ColumnType> res = new ArrayList<>(columnCount);
        res.add(ColumnType.CITY);
        res.add(ColumnType.COUNTRY);
        res.add(ColumnType.RIVER);
        res.addAll(rand.ints(3, ColumnType.values().length).distinct().limit(columnCount - 3).sorted().mapToObj(i -> ColumnType.values()[i]).collect(Collectors.toList()));
        return res;
    }
}
