package main;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

import drawing.*;
import entities.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public MainFrame() throws IOException {
        this.setTitle("Doodle Jump");
        this.setSize(480, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // создаем объекты
        entityDoodleGuy menuDoodle = new entityDoodleGuy(170, 400);
        entityDoodleGuy player = new entityDoodleGuy(100, 200);

        SettingsPanel settingsPanel = new SettingsPanel(
            () -> {
                cardLayout.show(mainContainer, "MENU");
            }
        );
        GamePanel gamePanel = new GamePanel(player);

        // таймер игры
        Timer timer = new Timer(1000 / 60, e -> {
            player.update();
            player.checkCollision(gamePanel.getPlatforms());
            gamePanel.repaint();
        });

        // меню игры
        MenuPanel menuPanel = new MenuPanel(menuDoodle, 
            () -> {
            cardLayout.show(mainContainer, "GAME");
            timer.start(); 
        }, 
        () -> cardLayout.show(mainContainer, "OPTIONS"));

        // контейнеры
        mainContainer.add(menuPanel, "MENU");
        mainContainer.add(gamePanel, "GAME");
        mainContainer.add(settingsPanel, "OPTIONS");

        add(mainContainer);

        this.setVisible(true);

        // генерируем уровень
        gamePanel.generateLevel();
    }
}
