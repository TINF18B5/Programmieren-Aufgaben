package de.dhbwka.programmieren.uebungen.stadtlandfluss;

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
        //TODO actually do stuff
        for(Sheet sheet : this.sheets) {
            for(ColumnType column : this.columns) {
                System.out.printf("%s: %s%n", column.getTitle(), sheet.getInputFor(column));
            }
        }
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
