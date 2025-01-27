package de.dhbwka.programmieren.uebungen.stadtlandfluss;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Sheet extends JFrame {
   
    
    public Sheet(Player player, Game game) {
    	 
    	this.setSize(500,500);
    	this.setTitle(player.getName());
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLayout(new BorderLayout());
    	
    	JPanel panelNorth= new JPanel(new GridLayout(3,2));
    	JLabel punkte= new JLabel("Punkte");
    	JLabel sekunden= new JLabel("Verbleibende Sekunden");
    	JLabel buchstabe= new JLabel("Anfangsbuchstabe");
    	JLabel zero= new JLabel("0");
    	JLabel f�nfzehn= new JLabel(" ");
    	JLabel a= new JLabel(" ");
    	panelNorth.add(punkte);
    	panelNorth.add(zero);
    	panelNorth.add(sekunden);
    	panelNorth.add(f�nfzehn);
    	panelNorth.add(buchstabe);
    	panelNorth.add(a);
    	this.add(panelNorth, BorderLayout.NORTH);
    	
    	JPanel panelSouth= new JPanel();
    	JButton buttonStart= new JButton("Start");
    	JButton buttonStop= new JButton("Stop");
    	panelSouth.add(buttonStart);
    	panelSouth.add(buttonStop);
    	this.add(panelSouth, BorderLayout.SOUTH);
    	this.setVisible(true);
    	
    	JLabel noGame= new JLabel("Kein Spiel aktiv.");
    	this.add(noGame, BorderLayout.CENTER);
        
    	
    }
    
    public void gameStopped() {

    }
    
    public void gameStarted(Collection<ColumnType> columnTypes, char firstLetter) {
    
    }
    
    public void setRemainingSeconds(int remainingSeconds) {
    
    }
    
    public void updateScore() {
    
    }
    
    public String getInputFor(ColumnType columnType) {
        //return this.center instanceof InputGrid ? ((InputGrid) this.center).getInputFor(columnType) : "";
    	return null;
    }
    
    public void setScoreFor(ColumnType columnType, int score) {
        //if(this.center instanceof InputGrid) {
        //    ((InputGrid) this.center).setScoreFor(columnType, score);
        //}
    }
    
    
    private static final class InputGrid extends JPanel {
    
        private final Map<ColumnType, JTextField> inputs;
        private final Map<ColumnType, JLabel> scores;
        
        public InputGrid(Collection<ColumnType> columns) {
            super(new GridLayout(columns.size(), 3, 5, 5));
            this.inputs = new HashMap<>(columns.size());
            this.scores = new HashMap<>(columns.size());
    
            for(ColumnType column : columns) {
                this.add(new JLabel(column.getTitle()));
                final JTextField input = new JTextField();
                this.add(input);
                final JLabel score = new JLabel(Integer.toString(0, 10));
                this.add(score);
    
                this.inputs.put(column, input);
                this.scores.put(column, score);
            }
        }
        
        public String getInputFor(ColumnType columnType) {
            final JTextField jTextField = this.inputs.get(columnType);
            return jTextField != null ? jTextField.getText() : "";
        }
        
        public void setScoreFor(ColumnType columnType, int score) {
            final JLabel jLabel = this.scores.get(columnType);
            if(jLabel != null) {
                jLabel.setText(Integer.toString(score, 10));
            }
        }
    }
}
