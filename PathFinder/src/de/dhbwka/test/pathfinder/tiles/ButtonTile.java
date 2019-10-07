package de.dhbwka.test.pathfinder.tiles;

import de.dhbwka.test.pathfinder.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonTile extends JButton {
    
    private final Tile tile;
    private final Timer timer;
    
    public ButtonTile(Tile tile, PlayerWindow.GuiContent guiContent) {
        super(tile.getIcon());
        this.timer = new Timer(200, (e) -> this.updateIcons());
    
        this.tile = tile;
        this.addActionListener(e -> {
            if(tile.canBeRotated()) {
                tile.rotate();
                this.setIcon(tile.getIcon());
                guiContent.afterClick();
            }
        });
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                ButtonTile.this.timer.restart();
            }
        });
    }
    
    
    public void updateIcons() {
        if(this.getHeight() == 0 || this.getWidth() == 0) {
            this.setIcon(tile.getIcon());
        } else {
            int max = Math.max(this.getHeight(), this.getWidth());
            final ImageIcon icon = tile.getIcon();
            final Image image = icon.getImage();
            this.setIcon(new ImageIcon(image.getScaledInstance(max, max, 0)));
        }
    }
}
