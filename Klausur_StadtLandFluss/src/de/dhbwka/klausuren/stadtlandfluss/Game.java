package de.dhbwka.klausuren.stadtlandfluss;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Game {
    
    private static final Path filepath = Paths.get("validwords.txt");
    
    private final List<ColumnType> columns;
    private final Random rand;
    private final int minColumns;
    private final int maxColumns;
    private final MyTimerThread timer;
    private final Set<Sheet> sheets;
    private final Set<String> validWordsLowerCased;
    
    private char firstLetter;
    
    public Game(int min, int max, int seconds) {
        this.minColumns = min;
        this.maxColumns = max;
        this.timer = new MyTimerThread(seconds, this);
        this.timer.start();
        this.columns = new ArrayList<>();
        this.rand = new Random();
        this.sheets = new HashSet<>();
        
        this.firstLetter = '\0';
        validWordsLowerCased = readValidWords();
    }
    
    private Set<String> readValidWords() {
        try(final BufferedReader reader = Files.newBufferedReader(filepath)) {
            return reader.lines().map(String::toLowerCase).collect(Collectors.toSet());
        } catch(IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }
    
    private void createColumns() {
        this.columns.clear();
        this.columns.add(ColumnType.CITY);
        this.columns.add(ColumnType.COUNTRY);
        this.columns.add(ColumnType.RIVER);
        
        final ColumnType[] values = ColumnType.values();
        final List<ColumnType> columnTypes = Arrays.asList(values).subList(3, values.length);
        
        Collections.shuffle(columnTypes, rand);
        this.columns.addAll(columnTypes.subList(0, rand.nextInt(maxColumns - (minColumns - 3))));
    }
    
    private void createLetter() {
        this.firstLetter = (char) ('A' + rand.nextInt(26));
    }
    
    public void register(Sheet sheet) {
        this.sheets.add(sheet);
    }
    
    public void startGame() {
        
        createLetter();
        createColumns();
        
        for(Sheet sheet : sheets) {
            sheet.start(columns);
            sheet.setLetter(firstLetter);
        }
        timer.startMyTimer();
    }
    
    public void stopGame() {
        for(Sheet sheet : this.sheets) {
            sheet.stop();
        }
        timer.stopMyTimer();
        
        
        checkAnswers();
    }
    
    private void checkAnswers() {
        //Go through each column
        for(ColumnType column : columns) {
            
            //Create a map with every player's answer (lower-cased)
            final Map<String, Set<Sheet>> answers = new HashMap<>(columns.size());
            for(Sheet sheet : sheets) {
                final String textFor = sheet.getTextFor(column).trim();
                final String answer = textFor.toLowerCase(Locale.ENGLISH);
                answers.computeIfAbsent(answer, ignored -> new HashSet<>()).add(sheet);
            }
            
            //Remove empty answers
            //players with no answer (trimmed, empty string) get Scoring.NO_ANSWER points
            if(answers.containsKey("")) {
                for(Sheet sheet : answers.get("")) {
                    sheet.grantPointsFor(column, Scoring.NO_ANSWER.getPoints());
                }
                answers.remove("");
            }
            
            //Remove invalids
            //players with an invalid answer (trimmed, empty string) get Scoring.INCORRECT_ANSWER points
            for(String s : answers.keySet()) {
                if(!isCorrect(s, column)) {
                    answers.get(s).forEach(sheet -> sheet.grantPointsFor(column, Scoring.INCORRECT_ANSWER.getPoints()));
                    answers.remove(s);
                }
            }
            
            //No-one answered this one (correctly)
            if(answers.isEmpty()) {
                continue;
            }
            
            //Go through the correct answers
            //Players get Scoring.SINGLE_UNIQUE points if only one player had the correct answer
            //They get Scoring.UNIQUE points if they were the only one with the specific answer
            //Otherwise they get Scoring.CORRECT points
            final boolean onlyOneAnswer = answers.size() == 1;
            for(Set<Sheet> value : answers.values()) {
                if(value.size() == 1) {
                    value.iterator().next().grantPointsFor(column, (onlyOneAnswer ? Scoring.SINGLE_UNIQUE : Scoring.UNIQUE).getPoints());
                } else {
                    for(Sheet sheet : value) {
                        sheet.grantPointsFor(column, Scoring.CORRECT.getPoints());
                    }
                }
            }
        }
    }
    
    private boolean isCorrect(String lowercasedString, ColumnType type) {
        if(!lowercasedString.startsWith(Character.toString(Character.toLowerCase(firstLetter))))
            return false;
        if(validWordsLowerCased.contains(lowercasedString))
            return true;
        
        final int option = JOptionPane.showConfirmDialog(null, String.format(Locale.ENGLISH, "Ist '%s' korrekt für die Kategorie '%s'?", lowercasedString, type), "Option auswählen", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option != 0) {
            return false;
        }
        
        this.validWordsLowerCased.add(lowercasedString);
        
        try(final BufferedWriter writer = Files.newBufferedWriter(filepath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(lowercasedString);
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public void updateTime(int time) {
        for(Sheet sheet : sheets) {
            sheet.setRemainingTime(time);
        }
    }
}
