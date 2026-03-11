package drawing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
    private Image background;
    
    private BufferedImage image_button_menu;

    public SettingsPanel(Runnable onMenu) throws IOException {
        int buttonWidth = 130;
        int buttonHeight = 50;
        this.background = new ImageIcon(getClass().getResource("/res/background/options_bg.png")).getImage();

        this.image_button_menu = ImageIO.read(getClass().getResource("/res/buttons/menu_hover.png"));
        this.image_button_menu = resize(image_button_menu, buttonWidth, buttonHeight);

        ImageIcon buttonIconMenu = new ImageIcon(image_button_menu);

        setLayout(null);

        JButton menuButton = new JButton(buttonIconMenu);

        menuButton.setBounds(280, 580, buttonWidth, buttonHeight);

        add(menuButton);

        menuButton.setContentAreaFilled(false); // Убирает заливку
        menuButton.setBorderPainted(false);    // Убирает рамку
        menuButton.setFocusPainted(false);     // Убирает контур фокуса

        menuButton.addActionListener(e -> {
            onMenu.run();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // фон
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
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
}