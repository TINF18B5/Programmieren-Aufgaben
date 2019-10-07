package dendhbwka.practice.speeddating;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FrameSpeedDating extends JFrame {
    
    
    public FrameSpeedDating(List<String> men, List<String> women) {
        super("Speed Dating");
    
        if(men == null || women == null || men.size() != women.size()) {
            throw new IllegalArgumentException("Invalid list input");
        }
        final int size = men.size();
        
        final JPanel panel = new JPanel(new GridLayout(size, 2, 5, 5));
    
        final JLabel[] labelsMen = new JLabel[size];
        final JLabel[] labelsWomen = new JLabel[size];
    
    
        for(int i = 0; i < size; i++) {
            final JLabel manLabel = new JLabel(men.get(i));
            panel.add(manLabel);
            labelsMen[i] = manLabel;
    
            final JLabel womanLabel = new JLabel(women.get(i));
            panel.add(womanLabel);
            labelsWomen[i] = womanLabel;
        }
        
        
        this.add(panel, BorderLayout.CENTER);
        final JPanel south = new JPanel();
        final JButton comp = new JButton("Shuffle");
        comp.addActionListener(e -> {
            Collections.shuffle(men);
            Collections.shuffle(women);
    
            for(int i = 0; i < men.size(); i++) {
                labelsMen[i].setText(men.get(i));
                labelsWomen[i].setText(women.get(i));
            }
        });
        south.add(comp);
        this.add(south, BorderLayout.SOUTH);
    
    
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);
    
        EventQueue.invokeLater(() -> this.setVisible(true));
    }
}
