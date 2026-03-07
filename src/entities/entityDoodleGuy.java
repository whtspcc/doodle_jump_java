package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class entityDoodleGuy {
    private double x;
    private double y;

    private BufferedImage image;

    // конструктор
    public entityDoodleGuy(double x, double y) throws IOException {
        this.x = x;
        this.y = y;
        this.image = ImageIO.read(getClass().getResource("/res/player/left.png"));

        this.image = resize(this.image, 60,60);
    }

    // метод отрисовки картинки
    public void paint(Graphics g) {
        g.drawImage(this.image, (int) x, (int) y, null);
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
