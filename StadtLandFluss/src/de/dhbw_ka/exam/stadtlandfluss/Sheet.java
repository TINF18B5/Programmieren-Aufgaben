package de.dhbw_ka.exam.stadtlandfluss;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Sheet extends JFrame {
    
    private final Player player;
    private final Game game;
    private final JLabel notStartedLabel;
    
    private final TopBar topBar;
    
    private final JButton startButton, stopButton;
    private GameContent gameContent;
    private boolean gameWasStarted = false;
    
    public Sheet(Player player, Game game) {
        super(player.getName());
        this.setLayout(new BorderLayout(10, 10));
        this.player = player;
        this.game = game;
        
        this.notStartedLabel = new JLabel("Kein Spiel aktiv");
        notStartedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notStartedLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.add(notStartedLabel);
        
        this.topBar = new TopBar(player);
        this.add(topBar, BorderLayout.NORTH);
        
        
        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(this.startButton = new JButton("Start"));
        buttonsPanel.add(this.stopButton = new JButton("Stop"));
        this.stopButton.setEnabled(false);
        
        this.startButton.addActionListener(e -> game.startGame());
        this.stopButton.addActionListener(e -> game.stopGame());
        
        this.add(buttonsPanel, BorderLayout.SOUTH);
        
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);
        EventQueue.invokeLater(() -> this.setVisible(true));
    }
    
    public void gameStarted() {
        if(gameWasStarted) {
            this.remove(gameContent);
        } else {
            this.remove(notStartedLabel);
        }
        
        this.gameWasStarted = true;
        
        
        this.topBar.setPoints(player.getPoints());
        this.topBar.setRemainingSeconds(game.getSeconds());
        this.topBar.setFirstCharacter(game.getCurrentFirstCharacter());
        
        this.add(this.gameContent = new GameContent(game.getCurrentColumns()), BorderLayout.CENTER);
        this.startButton.setEnabled(false);
        this.stopButton.setEnabled(true);
        
        this.repaint();
        this.revalidate();
    }
    
    public void gameStopped() {
        this.startButton.setEnabled(true);
        this.stopButton.setEnabled(false);
        
        this.repaint();
        this.revalidate();
    }
    
    public String getInputFor(ColumnType type) {
        if(this.gameContent == null)
            return "";
        
        final ColumnContent columnContent = this.gameContent.contentMap.get(type);
        return columnContent == null ? "" : columnContent.inputText.getText();
    }
    
    public void grantPoints(ColumnType type, int points) {
        this.topBar.setPoints(this.player.addPoints(points));
        if(this.gameContent == null)
            return;
        
        final ColumnContent columnContent = this.gameContent.contentMap.get(type);
        if(columnContent != null)
            columnContent.setPoints(points);
    }
    
    public void setRemainingTime(int remainingTime) {
        this.topBar.setRemainingSeconds(remainingTime);
    }
    
    
    private static final class TopBar extends JPanel {
        
        private final JLabel points, remainingSeconds, firstCharacter;
        
        public TopBar(Player player) {
            super(new GridLayout(3, 2, 5, 5));
            
            this.add(new JLabel("Punkte"));
            points = new JLabel();
            this.setPoints(player.getPoints());
            this.add(points);
            
            this.add(new JLabel("Verbleibende Sekunden"));
            remainingSeconds = new JLabel();
            //this.setRemainingSeconds(-1);
            this.add(remainingSeconds);
            
            
            this.add(new JLabel("Anfangsbuchstabe"));
            this.firstCharacter = new JLabel();
            this.add(firstCharacter);
        }
        
        private void setPoints(int points) {
            this.points.setText(Integer.toString(points, 10));
        }
        
        private void setRemainingSeconds(int remainingSeconds) {
            this.remainingSeconds.setText(Integer.toString(remainingSeconds, 10));
        }
        
        private void setFirstCharacter(char firstCharacter) {
            this.firstCharacter.setText(Character.toString(firstCharacter));
        }
    }
    
    private static final class GameContent extends JPanel {
        
        private final Map<ColumnType, ColumnContent> contentMap;
        
        private GameContent(List<ColumnType> columns) {
            super(new GridLayout(columns.size(), 1, 5, 5));
            this.contentMap = columns.stream().map(ColumnContent::new).peek(this::add).collect(Collectors.toMap(ColumnContent::getType, Function.identity()));
        }
    }
    
    private static final class ColumnContent extends JPanel {
        
        private final ColumnType type;
        private final JTextField inputText;
        private final JLabel points;
        
        private ColumnContent(ColumnType type) {
            super(new GridLayout(1, 3));
            this.type = type;
            this.add(new JLabel(type.getTitle()));
            this.add(this.inputText = new JTextField());
            this.add(this.points = new JLabel(Integer.toString(0, 10)));
        }
        
        private ColumnType getType() {
            return type;
        }
        
        private void setPoints(int points) {
            this.points.setText(Integer.toString(points, 10));
        }
    }
}
