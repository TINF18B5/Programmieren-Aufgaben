package de.dhbwka.klausuren.jbay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class BieterTerminal extends JFrame {
    private final Bieter bieter;
    private final Auktionshaus auktionshaus;
    
    public BieterTerminal(Bieter bieter, Auktionshaus auktionshaus) {
        super(String.format(Locale.ENGLISH, "%s's Terminal", bieter.getFullName()));
        this.bieter = bieter;
        this.auktionshaus = auktionshaus;
    
        final JLabel timeLabel = new JLabel(Calendar.getInstance().getTime().toString());
        this.add(timeLabel, BorderLayout.NORTH);
        
        final Timer timer = new Timer("updateTimerLabel", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLabel.setText(Calendar.getInstance().getTime().toString());
            }
        }, 1000, 1000);
    
        this.updateTerminal();
    
        this.setLocationRelativeTo(null);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        EventQueue.invokeLater(() -> BieterTerminal.this.setVisible(true));
    }
    
    public void updateTerminal() {
        final JPanel panel = new JPanel(new GridLayout(auktionshaus.getAuktionen().size(), 5, 5, 5));
    
        for(Auktion auktion : auktionshaus.getAuktionen()) {
            panel.add(new JLabel(auktion.getWare().getTitle()));
            panel.add(new JLabel(Double.toString(auktion.getPreis())));
        
            panel.add(new JLabel(Optional.ofNullable(auktion.getMaxGebot()).map(a -> a.getBieter().getFullName()).orElse("---")));
            panel.add(new JLabel(auktion.getEnd().getTime().toString()));
            final JButton button = new JButton("Gebot");
        
            button.addActionListener(e -> {
                if(auktion.isOver()) {
                    JOptionPane.showMessageDialog(BieterTerminal.this, "Die Auktion ist leider schon vorbei...", "Meldung", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            
                final double max = BieterTerminal.this.readBid(auktion.getMinBiddingValue());
            
                //Just in case lets check again
                if(auktion.isOver()) {
                    JOptionPane.showMessageDialog(BieterTerminal.this, "Die Auktion ist leider schon vorbei...", "Meldung", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            
                final Gebot g = new Gebot(BieterTerminal.this.bieter, max);
                if(auktion.gebotAbgegeben(g)) {
                    JOptionPane.showMessageDialog(BieterTerminal.this, "Sie sind Höchstbietender!", "Meldung", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(BieterTerminal.this, "Gebot zu gering!", "Meldung", JOptionPane.INFORMATION_MESSAGE);
                }
            
                BieterTerminal.this.auktionshaus.gebotAbgegeben(auktion, g);
            });
        
            panel.add(button);
        }
        this.add(panel, BorderLayout.CENTER);
    }
    
    private double readBid(double minValue) {
        try {
            final String s = JOptionPane.showInputDialog(this, String.format(Locale.ENGLISH, "Bitte neues Gebot eingeben.%nMindestens%f Euro", minValue), Double.toString(minValue), JOptionPane.QUESTION_MESSAGE);
            return s != null ? Double.parseDouble(s) : 0.0D;
        } catch(NumberFormatException ignored) {
            return 0.0;
        }
    }
}
