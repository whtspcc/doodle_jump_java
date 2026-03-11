package drawing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import entities.entityDoodleGuy;
import entities.entityPlatform;

public class GamePanel extends JPanel {
    private int lastMouseX; // Переменная для хранения предыдущей позиции

    private entityDoodleGuy player;
    private List<entityPlatform> platforms;
    private Image background;

    public GamePanel(entityDoodleGuy player) {
        this.player = player;
        this.platforms = new ArrayList<>();
        this.background = new ImageIcon(getClass().getResource("/res/background/default.png")).getImage();

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int currentMouseX = e.getX();
                
                // Вычисляем смещение
                int deltaX = currentMouseX - lastMouseX;
                
                // Передаем смещение в метод игрока
                player.moveRelative(deltaX);
                
                // Обновляем позицию для следующего события
                lastMouseX = currentMouseX;
            }
        });
    }

    public void addPlatform(entityPlatform p) {
        platforms.add(p);
    }

    public List<entityPlatform> getPlatforms() {
        return this.platforms;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Рисуем фон
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // Рисуем платформы
        for (entityPlatform p : platforms) {
            p.paint(g);
            //p.drawHitbox(g);
        }

        // Рисуем игрока
        player.paint(g);
        //player.drawHitbox(g);
    }

    // рандомно генерировать платформы
    public void generateLevel() {
        Random random = new Random();
        int platformCount = 8;
        int currentY = 500;

        for (int i = 0; i < platformCount; i++) {
            int x = random.nextInt(getWidth() - 50);

            int y = currentY - (random.nextInt(100));

            try {
                addPlatform(new entityPlatform(x, y));
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentY = y;
        }
    }

    // реализовать позже логику проигрыша
    // public void endLevel() {
        
    // }
}