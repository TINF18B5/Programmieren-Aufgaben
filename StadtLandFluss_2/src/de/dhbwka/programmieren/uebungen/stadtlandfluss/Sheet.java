package de.dhbwka.programmieren.uebungen.stadtlandfluss;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Sheet extends JFrame {
    
    private final Player player;
    private final JLabel scoreLabel;
    private final JLabel remainingSecondsLabel;
    private final JLabel firstLetterLabel;
    
    private final JButton startButton;
    private final JButton stopButton;
    
    private JPanel center;
    
    public Sheet(Player player, Game game) {
        super(player.getName());
        
        this.player = player;
    
        final JPanel topPanel = new JPanel(new GridLayout(3, 2));
        topPanel.add(new JLabel("Punkte"));
        topPanel.add(this.scoreLabel = new JLabel(Integer.toString(0, 10), SwingConstants.LEFT));
        topPanel.add(new JLabel("Verbleibende Sekunden"));
        topPanel.add(this.remainingSecondsLabel = new JLabel());
        topPanel.add(new JLabel("Anfangsbuchstabe"));
        topPanel.add(this.firstLetterLabel = new JLabel());
        this.add(topPanel, BorderLayout.NORTH);
    
    
        this.center = new JPanel();
        this.center.add(new JLabel("Kein Spiel aktiv.", SwingConstants.CENTER));
        this.add(this.center, BorderLayout.CENTER);
    
    
        final JPanel bottomPanel = new JPanel();
        this.startButton = new JButton("Start");
        this.stopButton = new JButton("Stop");
        this.stopButton.setEnabled(false);
        
        this.startButton.addActionListener(e-> game.startGame());
        this.stopButton.addActionListener(e-> game.stopGame());
        
        bottomPanel.add(startButton);
        bottomPanel.add(stopButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
        
    
    
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);
        EventQueue.invokeLater(() -> Sheet.this.setVisible(true));
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void gameStopped() {
        this.startButton.setEnabled(true);
        this.stopButton.setEnabled(false);
    }
    
    public void gameStarted(Collection<ColumnType> columnTypes, char firstLetter) {
        this.startButton.setEnabled(false);
        this.stopButton.setEnabled(true);
        
        this.remove(center);
        this.center = new InputGrid(columnTypes);
        this.add(this.center);
        
        this.firstLetterLabel.setText(Character.toString(firstLetter));
    }
    
    public void setRemainingSeconds(int remainingSeconds) {
        this.remainingSecondsLabel.setText(Integer.toString(remainingSeconds, 10));
    }
    
    public void updateScore() {
        this.scoreLabel.setText(Integer.toString(this.player.getScore(), 10));
    }
    
    public String getInputFor(ColumnType columnType) {
        return this.center instanceof InputGrid ? ((InputGrid) this.center).getInputFor(columnType) : "";
    }
    
    public void setScoreFor(ColumnType columnType, int score) {
        if(this.center instanceof InputGrid) {
            ((InputGrid) this.center).setScoreFor(columnType, score);
        }
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
