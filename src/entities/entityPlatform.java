package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class entityPlatform {
    private double x;
    private double y;
    private final int width = 50, height = 15;
    private Rectangle hitbox;

    private BufferedImage spriteSheet;
    private BufferedImage image;

    public entityPlatform(double x, double y) throws IOException {
        this.x = x;
        this.y = y;

        this.spriteSheet = ImageIO.read(getClass().getResource("/res/game_tiles/default.png"));

        this.image = spriteSheet.getSubimage(0, 0, 60, 18);

        this.image = resize(this.image, 50,15);

        initHitbox();
    }

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

    public void drawHitbox(Graphics g) {
        // для дебага
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, width, height); 
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
