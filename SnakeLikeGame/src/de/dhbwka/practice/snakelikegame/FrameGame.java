package de.dhbwka.practice.snakelikegame;

import javax.swing.*;
import java.awt.*;

public class FrameGame extends JFrame {
    
    public FrameGame() {
        super("Ding");
        
        final JPanel panel = new JPanel(new GridLayout(10, 10, 5, 5));
        
        JLabel[][] labels = new JLabel[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                final JLabel jLabel = new JLabel(" ");
                jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(jLabel);
                labels[i][j] = jLabel;
            }
        }
        
        
        labels[9][9].setText("B");
        labels[0][0].setText("A");
        
        
        this.add(panel, BorderLayout.CENTER);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        EventQueue.invokeLater(() -> this.setVisible(true));
        
        new MyTimer(labels).start();
    }
    
    private static final class MyTimer extends Thread {
    
        private final JLabel[][] labels;
        private int counter = 0;
        private int posA = 0;
        private int posB = 9;
    
        public MyTimer(JLabel[][] labels) {
            this.labels = labels;
        }
    
        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(1000, 0);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                
                counter++;
                
                if(counter >= 19) {
                    //Nichts
                    return;
                } else if(counter >= 10){
                    //A sich bewegen
                    labels[0][posA].setText(" ");
                    labels[0][++posA].setText("A");
                } else {
                    //B muss sich bewegen
                    labels[9][posB].setText(" ");
                    labels[9][--posB].setText("B");
                }
            }
        }
    }
}
