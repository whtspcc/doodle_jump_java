package drawing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

import entities.*;

public class MenuPanel extends JPanel{
    private Image background;
    private entityDoodleGuy jumpingDoodle;
    private List<entityPlatform> platforms;
    private Timer menuAnimation;

    private BufferedImage image_button_play;
    private BufferedImage image_button_settings;

    public MenuPanel(entityDoodleGuy jumpingDoodle, Runnable onStartGame, Runnable onSettings) throws IOException {
        int buttonWidth = 130;
        int buttonHeight = 50;

        this.jumpingDoodle = jumpingDoodle;
        this.platforms = new ArrayList<>();
        this.background = new ImageIcon(getClass().getResource("/res/background/main_menu.png")).getImage();

        // Загружаем и масштабируем изображение кнопки
        this.image_button_play = ImageIO.read(getClass().getResource("/res/buttons/play.png"));
        this.image_button_play = resize(image_button_play, buttonWidth, buttonHeight);

        this.image_button_settings = ImageIO.read(getClass().getResource("/res/buttons/options.png"));
        this.image_button_settings = resize(image_button_settings, buttonWidth, buttonHeight);

        // Создаем иконку из масштабированного изображения
        ImageIcon buttonIconPlay = new ImageIcon(image_button_play);
        ImageIcon buttonIconSettings = new ImageIcon(image_button_settings);

        setLayout(null);
        
        // Создаем кнопку с иконкой
        JButton playButton = new JButton(buttonIconPlay);
        JButton settingsButton = new JButton(buttonIconSettings);
        
        // Устанавливаем позицию и размер кнопки 
        playButton.setBounds(90, 160, buttonWidth, buttonHeight);

        settingsButton.setBounds(130, 240, buttonWidth, buttonHeight);
        
        // Настройка внешнего вида кнопки
        playButton.setContentAreaFilled(false); // Убирает заливку
        playButton.setBorderPainted(false);    // Убирает рамку
        playButton.setFocusPainted(false);     // Убирает контур фокуса

        settingsButton.setContentAreaFilled(false); // Убирает заливку
        settingsButton.setBorderPainted(false);    // Убирает рамку
        settingsButton.setFocusPainted(false);     // Убирает контур фокуса
        
        // Добавляем действие для кнопки
        playButton.addActionListener(e -> {
            stopAnimation();
            onStartGame.run();
        });
        
        settingsButton.addActionListener(e -> {
            onSettings.run();
        });

        // Добавляем кнопку на панель
        add(playButton);

        add(settingsButton);

        // таймер только для анимации прыжков в меню
        menuAnimation = new Timer(1000 / 60, e -> {
            jumpingDoodle.update(); // дудлик падает

            // если он упал слишком низко — заставляем его прыгнуть
            if (jumpingDoodle.getHitbox().y > 450) { 
                jumpingDoodle.jump();
            }
            
            repaint(); // перерисовываем панель, чтобы увидеть движение
        });

        addPlatform(new entityPlatform(50, 510));
        
        menuAnimation.start();
    }

    // метод изменения размеров картинки
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }  

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // фон
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // платформы
        for (entityPlatform p : platforms) {
            p.paint(g);
            //p.drawHitbox(g);
        }

        // рисуем игрока
        jumpingDoodle.paint(g);
        //jumpingDoodle.drawHitbox(g);
    }

    // добавить платформу
    public void addPlatform(entityPlatform p) {
        platforms.add(p);
    }

    // метод остановки анимации меню
    public void stopAnimation() {
        if (menuAnimation != null) {
            menuAnimation.stop();
        }
    }
}