package drawing;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import entities.entityDoodleGuy;
import entities.entityPlatform;

public class GamePanel extends JPanel{
    private entityDoodleGuy player;
    private List<entityPlatform> platforms;
    private Image background;

    public GamePanel(entityDoodleGuy player) {
        this.player = player;
        this.platforms = new ArrayList<>();
        this.background = new ImageIcon(getClass().getResource("/res/background/default.png")).getImage();
    }

    public void addPlatform(entityPlatform p) {
        platforms.add(p);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // рисуем bg    
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // рисуем платформы
        for (entityPlatform p : platforms) {
            p.paint(g);
        }

        player.paint(g);
    }
}
