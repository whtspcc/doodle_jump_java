package main;

import java.io.IOException;
import javax.swing.Timer;
import javax.swing.JFrame;

import drawing.*;
import entities.*;

public class MainFrame extends JFrame {

    public MainFrame() throws IOException {
        this.setTitle("Doodle Jump");
        this.setSize(480, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // создаем объекты
        entityDoodleGuy player = new entityDoodleGuy(100, 200);
        entityPlatform plat1 = new entityPlatform(300, 250);
        entityPlatform plat2 = new entityPlatform(150, 350);

        // создаем панель и закидываем в нее объекты
        GamePanel gamePanel = new GamePanel(player);
        gamePanel.addPlatform(plat1);
        gamePanel.addPlatform(plat2);

        add(gamePanel);

        this.setVisible(true); // показывать окно

        // окно всегда перерисовывается
        Timer timer = new Timer(1000 / 60, e -> this.repaint());
        timer.start();  
    }
}
