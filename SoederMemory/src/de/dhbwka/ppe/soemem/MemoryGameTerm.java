package de.dhbwka.ppe.soemem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class MemoryGameTerm extends JFrame {
    
    private final Timer counter;
    private final PlayerPanel playerPanel;
    private final MemoryPanel memoryPanel;
    private final MemoryGame game;
    private final AtomicInteger seconds;
    private int rounds = 0;
    
    public MemoryGameTerm(MemoryGame game) {
        this.game = game;
        this.seconds = new AtomicInteger(0);
        this.counter = new Timer("CounterTimer");
        this.counter.schedule(new TimerTask() {
            @Override
            public void run() {
                setTitle(String.format(Locale.ENGLISH, "Soeder Memory (%d)", seconds.incrementAndGet()));
            }
        }, 1000, 1000);
        
        this.playerPanel = new PlayerPanel(game.getPlayers());
        memoryPanel = new MemoryPanel(game);
        
        
        this.add(playerPanel, BorderLayout.NORTH);
        this.add(memoryPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        this.setSize(800, 600);
        this.roundStarted();
        EventQueue.invokeLater(() -> this.setVisible(true));
    }
    
    public void roundStarted() {
        rounds++;
        this.memoryPanel.roundStarted();
    }
    
    public void roundFinished(boolean wasMatch) {
        if(wasMatch) {
            game.getCurrentPlayer().addPoint();
        } else {
            JOptionPane.showMessageDialog(null, "Sorry, those don't match", "Wrong", JOptionPane.ERROR_MESSAGE);
            game.nextPlayer();
        }
        
        if(!memoryPanel.hasAvailableCards()) {
            playerPanel.updatePlayers();
            counter.cancel();
            for(Player player : game.getPlayers()) {
                player.setStatus(PlayerStatus.FINISHED);
            }
            
            //After the game re-disable all buttons to also disable the additional card
            for(MemoryButton card : memoryPanel.cards) {
                card.setEnabled(false);
            }
            
            this.showScores();
        }
        roundStarted();
        playerPanel.updatePlayers();
    }
    
    private void showScores() {
        final String current = game.getPlayers().stream().sorted(Comparator.comparingInt(Player::getPoints).reversed().thenComparing(Player::getName)).map(Player::toString).collect(Collectors.joining(", ", String.format(Locale.ENGLISH, "Game ends after %d rounds, scores: ", rounds), ""));
        
        String lastGames = "";
        final File file = new File("memory.txt");
        if(file.exists()) {
            try(final BufferedReader reader = Files.newBufferedReader(file.toPath())) {
                lastGames = reader.lines().collect(Collectors.joining("\n", "\n\nLast Games\n", ""));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        JOptionPane.showMessageDialog(null, current + lastGames, "Score", JOptionPane.INFORMATION_MESSAGE);
        
        try(final BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(current);
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static final class PlayerPanel extends JPanel {
        
        
        private final Map<Player, JLabel> players;
        
        public PlayerPanel(List<Player> playerList) {
            super(new GridLayout(playerList.size(), 1, 5, 5));
            
            this.players = new HashMap<>();
            
            
            for(Player player : playerList) {
                final JLabel label = new JLabel(player.toString());
                label.setBackground(player.getStatus().getColor());
                this.add(label);
                players.put(player, label);
            }
        }
        
        public void updatePlayers() {
            players.forEach((player, jLabel) -> {
                jLabel.setForeground(player.getStatus().getColor());
                jLabel.setText(player.toString());
            });
        }
        
        
    }
    
    private static final class MemoryButton extends JToggleButton {
        
        
        private final MemoryImages.MemoryImage button;
        
        private MemoryButton(MemoryImages.MemoryImage button) {
            this.button = button;
            
            this.setSelectedIcon(button.getImage());
            this.setIcon(MemoryImages.getBackside());
        }
        
        private boolean isBlank() {
            return button.getImage() == MemoryImages.getBlank();
        }
    }
    
    private final class MemoryPanel extends JPanel {
        
        private final List<MemoryButton> cards;
        private MemoryButton selectedCard;
        
        private MemoryPanel(MemoryGame game) {
            super(new GridLayout(game.getRows(), game.getCols(), 5, 5));
            game.nextPlayer();
            
            final List<MemoryImages.MemoryImage> buttons = new ArrayList<>(game.getCols() * game.getRows());
            
            buttons.addAll(game.getSelectedImages());
            buttons.addAll(game.getSelectedImages());
            if(game.isBlankRequired())
                buttons.add(new MemoryImages.MemoryImage("blank", MemoryImages.getBlank()));
            
            Collections.shuffle(buttons);
            
            selectedCard = null;
            this.cards = buttons.stream().map(MemoryButton::new).peek(button -> {
                this.add(button);
                button.addActionListener(ev -> cardSelected(button));
            }).collect(Collectors.toList());
            
            playerPanel.updatePlayers();
        }
        
        private boolean hasAvailableCards() {
            return cards.stream().anyMatch(c -> !c.isBlank() && c.isEnabled());
        }
        
        private void roundStarted() {
            this.selectedCard = null;
        }
        
        private void cardSelected(MemoryButton card) {
            if(this.selectedCard == null) {
                this.selectedCard = card;
                return;
            }
            
            if(card == this.selectedCard) {
                card.setSelected(true);
                return;
            }
            
            final boolean wasMatch = this.selectedCard.button == card.button;
            if(wasMatch) {
                card.setEnabled(false);
                this.selectedCard.setEnabled(false);
            } else {
                card.setSelected(false);
                this.selectedCard.setSelected(false);
            }
            roundFinished(wasMatch);
        }
    }
}
