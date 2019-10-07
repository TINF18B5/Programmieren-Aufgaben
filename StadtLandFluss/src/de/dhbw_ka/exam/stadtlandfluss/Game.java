package de.dhbw_ka.exam.stadtlandfluss;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Game {
    
    /**
     * The player was the only one to provide a correct answer for this column
     */
    private static final int SINGLE_CORRECT = 20;
    /**
     * The player was the only one to provide this unique answer for the column, but not the only one to provide a correct answer
     */
    private static final int UNIQUE_CORRECT = 10;
    /**
     * Several players provided the same answer for this column
     */
    private static final int MULTIPLE_CORRECT = 5;
    /**
     * The answer was incorrect
     */
    private static final int INCORRECT = 0;
    private static final Random random;
    private static final Path path;
    
    static {
        random = new Random();
        path = Paths.get("validwords.txt");
    }
    
    private final int minColumns;
    private final int maxColumns;
    private final int seconds;
    private final Set<Sheet> sheets;
    private final Set<String> validWords;
    private final TimerThread timer;
    private char currentFirstCharacter;
    private List<ColumnType> currentColumns;
    
    public Game(int minColumns, int maxColumns, int seconds) {
        this.minColumns = Math.max(minColumns, 3);
        this.maxColumns = Math.max(maxColumns, this.minColumns);
        this.seconds = seconds;
        
        
        this.currentFirstCharacter = '\0';
        this.sheets = new HashSet<>();
        this.validWords = readValidWords();
        this.timer = new TimerThread(seconds, this);
        this.timer.start();
    }
    
    private static Set<String> readValidWords() {
        try(final BufferedReader reader = Files.newBufferedReader(path)) {
            //The file should only contain lowercases already but just in case
            return reader.lines().map(String::toLowerCase).collect(Collectors.toSet());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return new HashSet<>(0);
    }
    
    
    private char createFirstCharacter() {
        return (char) ('A' + random.nextInt(26));
    }
    
    private List<ColumnType> createColumns() {
        final int columnCount = minColumns + random.nextInt(maxColumns - minColumns);
        final List<ColumnType> out = new ArrayList<>(columnCount);
        out.add(ColumnType.CITY);
        out.add(ColumnType.COUNTRY);
        out.add(ColumnType.RIVER);
        final List<ColumnType> otherTypes = Arrays.asList(ColumnType.values()).subList(3, ColumnType.values().length);
        Collections.shuffle(otherTypes, random);
        out.addAll(otherTypes.subList(0, columnCount - 3));
        
        return out;
    }
    
    public void register(Sheet sheet) {
        this.sheets.add(sheet);
    }
    
    public void startGame() {
        this.currentFirstCharacter = createFirstCharacter();
        this.currentColumns = createColumns();
        this.timer.restartMe();
        
        for(Sheet sheet : this.sheets) {
            sheet.gameStarted();
        }
    }
    
    public void stopGame() {
        this.timer.stopMe();
        
        for(Sheet sheet : sheets) {
            sheet.gameStopped();
        }
        
        this.evaluateInputs();
    }
    
    public char getCurrentFirstCharacter() {
        return currentFirstCharacter;
    }
    
    public List<ColumnType> getCurrentColumns() {
        return currentColumns;
    }
    
    public int getSeconds() {
        return seconds;
    }
    
    private void evaluateInputs() {
        for(ColumnType currentColumn : this.currentColumns) {
            evaluateColumn(currentColumn);
        }
    }
    
    private void evaluateColumn(ColumnType currentColumn) {
        final Map<String, List<Sheet>> answers = new HashMap<>(sheets.size());
        for(Sheet sheet : this.sheets) {
            final List<Sheet> value = new LinkedList<>();
            value.add(sheet);
            answers.merge(sheet.getInputFor(currentColumn).toUpperCase(Locale.ENGLISH), value, (sheets1, sheets2) -> {
                sheets1.addAll(sheets2);
                return sheets1;
            });
        }
        
        //Remove all incorrect answers
        final Iterator<String> iterator = answers.keySet().iterator();
        while(iterator.hasNext()) {
            final String answer = iterator.next();
            if(!isCorrect(answer, currentColumn)) {
                for(Sheet sheet : answers.get(answer))
                    sheet.grantPoints(currentColumn, INCORRECT);
                iterator.remove();
            }
        }
        
        //Only one correct answer and only given by one person
        if(answers.size() == 1 && answers.values().iterator().next().size() == 1) {
            answers.values().iterator().next().get(0).grantPoints(currentColumn, SINGLE_CORRECT);
            return;
        }
        
        //Several people answered correctly, the list contains who answered.
        //We don't need the actual answer here so we only iterate over the values
        for(List<Sheet> sheets : answers.values()) {
            if(sheets.size() == 1) {
                sheets.get(0).grantPoints(currentColumn, UNIQUE_CORRECT);
            } else {
                for(Sheet sheet : sheets) {
                    sheet.grantPoints(currentColumn, MULTIPLE_CORRECT);
                }
            }
        }
    }
    
    /**
     * @param answer The answer, already uppercased
     */
    private boolean isCorrect(String answer, ColumnType columnType) {
        if(answer == null || answer.length() <= 0 || answer.charAt(0) != this.currentFirstCharacter)
            return false;
        
        answer = answer.toLowerCase(Locale.ENGLISH);
        if(validWords.contains(answer))
            return true;
        
        final int i = JOptionPane.showOptionDialog(null, String.format(Locale.ENGLISH, "Ist '%s' korrekt für Kategorie '%s'?", answer, columnType.getTitle()), "Option auswählen", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Ja", "Nein", "Abbrechen"}, "Nein");
        if(i != 0)
            return false;
        
        this.validWords.add(answer);
        try(final BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(answer);
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    public Set<Sheet> getSheets() {
        return sheets;
    }
}
