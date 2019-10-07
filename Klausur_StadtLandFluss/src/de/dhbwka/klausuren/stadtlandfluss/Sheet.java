package de.dhbwka.klausuren.stadtlandfluss;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Sheet extends JFrame {
    
    private final JLabel points, letter, remainingTime;
    private final JButton start, stop;
    private final Player player;
    private final Map<ColumnType, MyRowPanel> shownTypes;
    private JComponent centerComponent;
    
    public Sheet(Player player, Game game) {
        super(player.getName());
        this.player = player;
        
        this.setLayout(new BorderLayout(5, 5));
        
        final JPanel top = new JPanel(new GridLayout(3, 2, 5, 5));
        top.add(new JLabel("Punkte"));
        top.add(this.points = new JLabel(Integer.toString(0, 10)));
        top.add(new JLabel("Verbleibende Sekunden"));
        top.add(this.remainingTime = new JLabel(""));
        top.add(new JLabel("Buchstabe"));
        top.add(this.letter = new JLabel(""));
        this.add(top, BorderLayout.NORTH);
        
        this.add(this.centerComponent = new JLabel("Kein Spiel aktiv", SwingConstants.CENTER), BorderLayout.CENTER);
        
        final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        this.start = new JButton("Start");
        start.setEnabled(true);
        start.addActionListener(e -> {
            game.startGame();
        });
        bottom.add(start);
        this.stop = new JButton("Stop");
        stop.setEnabled(false);
        stop.addActionListener(e -> {
            game.stopGame();
        });
        bottom.add(stop);
        
        this.add(bottom, BorderLayout.SOUTH);
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);
        EventQueue.invokeLater(() -> this.setVisible(true));
        this.shownTypes = new HashMap<>();
    }
    
    
    public void setRemainingTime(int remainingTime) {
        this.remainingTime.setText(Integer.toString(remainingTime, 10));
    }
    
    public void setLetter(char letter) {
        this.letter.setText(Character.toString(letter));
    }
    
    public void start(List<ColumnType> types) {
        for(MyRowPanel value : this.shownTypes.values()) {
            centerComponent.remove(value);
        }
        this.shownTypes.clear();
        this.remove(centerComponent);
        this.centerComponent = new JPanel(new GridLayout(types.size(), 1, 5, 5));
        
        for(ColumnType type : types) {
            final MyRowPanel row = new MyRowPanel(type);
            this.centerComponent.add(row);
            this.shownTypes.put(type, row);
        }
        
        this.add(centerComponent, BorderLayout.CENTER);
        this.start.setEnabled(false);
        this.stop.setEnabled(true);
    }
    
    public void stop() {
        this.start.setEnabled(true);
        this.stop.setEnabled(false);
    }
    
    public void grantPointsFor(ColumnType type, int points) {
        this.shownTypes.get(type).points.setText(Integer.toString(points, 10));
        this.points.setText(Integer.toString(this.player.addPoints(points), 10));
    }
    
    public String getTextFor(ColumnType type) {
        return this.shownTypes.get(type).inputText.getText();
    }
    
    private static final class MyRowPanel extends JPanel {
        
        private final JTextField inputText;
        private final JLabel points;
        
        public MyRowPanel(ColumnType type) {
            super(new GridLayout(1, 3, 5, 5));
            this.add(new JLabel(type.getTitle()));
            this.add(this.inputText = new JTextField());
            this.add(this.points = new JLabel("0"));
        }
    }
}
