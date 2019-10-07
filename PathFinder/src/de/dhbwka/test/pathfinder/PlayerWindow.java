package de.dhbwka.test.pathfinder;

import de.dhbwka.test.pathfinder.tiles.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

public class PlayerWindow extends JFrame {
    
    private final GuiTop guiTop;
    private final GuiContent guiContent;
    private final Game game;
    private int clicks;
    private int totalClicks;
    
    
    public PlayerWindow(String name, Game game) {
        super(name);
        this.game = game;
        
        this.add(this.guiTop = new GuiTop(), BorderLayout.NORTH);
        this.add(this.guiContent = this.new GuiContent(), BorderLayout.CENTER);
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        
        EventQueue.invokeLater(() -> this.setVisible(true));
    }
    
    public void gameIsOver() {
        JOptionPane.showMessageDialog(this, String.format(Locale.ENGLISH, "You have completed the game with %d total clicks", totalClicks), "Success", JOptionPane.INFORMATION_MESSAGE);
        guiContent.gameOver();
    }
    
    public void roundIsOver() {
        JOptionPane.showMessageDialog(this, String.format(Locale.ENGLISH, "You completed this level with %d clicks", clicks), "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void startRound(Level level) {
        this.guiTop.setClicks(clicks = 0);
        this.guiContent.setLevel(level);
    }
    
    
    public static final class GuiTop extends JPanel {
        
        private final JLabel clicksLabel;
        private final JLabel totalClicksLabel;
        
        public GuiTop() {
            super(new GridLayout(2, 2, 5, 5));
            this.add(new JLabel("Clicks: "));
            this.add(this.clicksLabel = new JLabel(Integer.toString(0, 10)));
            this.add(new JLabel("Clicks: "));
            this.add(this.totalClicksLabel = new JLabel(Integer.toString(0, 10)));
        }
        
        
        public void setClicks(int clicks) {
            this.clicksLabel.setText(Integer.toString(clicks, 10));
        }
        
        public void setTotalClicks(int clicks) {
            this.totalClicksLabel.setText(Integer.toString(clicks, 10));
        }
        
        
    }
    
    public final class GuiContent extends JPanel {
        
        private final List<ButtonTile> tileButtons;
        private Level level;
        
        public GuiContent() {
            this.tileButtons = new ArrayList<>();
        }
        
        public void setLevel(Level level) {
            this.level = level;
            
            this.removeAll();
            this.tileButtons.clear();
            this.setLayout(new GridLayout(level.getHeight(), level.getWidth(), 0, 0));
            
            for(Tile[] row : level.getTiles()) {
                for(Tile tile : row) {
                    final ButtonTile comp = new ButtonTile(tile, this);
                    this.tileButtons.add(comp);
                    this.add(comp);
                }
            }
            
            this.redraw();
            this.repaint();
            this.revalidate();
        }
        
        public void afterClick() {
            guiTop.setClicks(++clicks);
            guiTop.setTotalClicks(++totalClicks);
            redraw();
        }
        
        public void redraw() {
            Set<Tile> activeTiles = new HashSet<>(level.getHeight() * level.getWidth());
            Queue<Tile> tileQueue = new LinkedList<>();
            
            for(Tile[] row : level.getTiles()) {
                for(Tile tile : row) {
                    tile.resetInputSides();
                    if(tile.isActive() && activeTiles.add(tile)) {
                        tileQueue.add(tile);
                    }
                }
            }
            
            
            while(!tileQueue.isEmpty()) {
                final Tile poll = tileQueue.poll();
                for(Side outputSide : poll.getOutputSides()) {
                    final int newRow = poll.getRow() + outputSide.getRowOffset();
                    final int newCol = poll.getColumn() + outputSide.getColumnOffset();
                    
                    if(newRow >= level.getHeight() || newCol >= level.getWidth()) {
                        continue;
                    }
                    
                    if(newRow < 0 || newCol < 0) {
                        continue;
                    }
                    
                    final Tile otherTile = level.getTileAt(newRow, newCol);
                    otherTile.inputOnSide(outputSide.getOpposite());
                    if(otherTile.isActive() && activeTiles.add(otherTile)) {
                        activeTiles.add(otherTile);
                        tileQueue.add(otherTile);
                    }
                }
            }
            
            for(ButtonTile panel : this.tileButtons) {
                panel.updateIcons();
            }
            
            if(isFinished()) {
                game.finishedLevel();
            }
        }
        
        public boolean isFinished() {
            for(Tile[] row : level.getTiles()) {
                for(Tile tile : row) {
                    if(tile.isRequired() && !tile.isActive())
                        return false;
                }
            }
            return true;
        }
        
        public void gameOver() {
            for(ButtonTile tileButton : this.tileButtons) {
                tileButton.setEnabled(false);
            }
        }
    }
}
