package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.List;


public class entityDoodleGuy {
    private double x;
    private double y;
    private final int width = 60, height = 60;
    private double velocity = 0; // скорость прыжка
    private double gravity = 0.5;// скорость падения 
    private Rectangle hitbox; // позже добавить логику хитбоксов
    private int groundLevel = 600;
    private boolean facingRight = true; // flag: смотрит ли направо?
                                        // true - да -> спрайт right
                                        // false - нет -> спрайт left

    private BufferedImage playerLeft, playerLeftJump, playerRight, playerRightJump;

    // конструктор
    public entityDoodleGuy(double x, double y) throws IOException {
        this.x = x;
        this.y = y;

        this.playerLeft = ImageIO.read(getClass().getResource("/res/player/left.png"));
        this.playerLeftJump = ImageIO.read(getClass().getResource("/res/player/left_jump.png"));
        this.playerRight = ImageIO.read(getClass().getResource("/res/player/right.png"));
        this.playerRightJump = ImageIO.read(getClass().getResource("/res/player/right_jump.png"));

        this.playerLeft = resize(this.playerLeft, width, height);
        this.playerLeftJump = resize(this.playerLeftJump, width, height);
        this.playerRight = resize(this.playerRight, width,height);
        this.playerRightJump = resize(this.playerRightJump, width,height);

        initHitbox();
    }

    // метод отрисовки картинки
    public void paint(Graphics g) {
        BufferedImage currentImage;
        
        // меняем спрайт
        if (facingRight) {
            currentImage = (velocity < 0) ? playerRightJump : playerRight;
        } else {
            currentImage = (velocity < 0) ? playerLeftJump : playerLeft;
        }

        g.drawImage(currentImage, (int) x - 130, (int) y, null);
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

    public void jump() {
        velocity = -15;
    }

    public void update() {
        velocity += gravity; 
        y += velocity;

         // проверяем, землю
        if (y >= groundLevel) {
            y = groundLevel; // чтобы не провалился
            jump();          // снова толкаем вверх
        }
        
        updateHitbox();
    }

    public void moveRelative(int dx) {
        this.x += dx;
        
        // здесь меняем флаг
        if (dx > 0) {
            facingRight = true;
        } else {
            facingRight = false;
        }
    }

    public void drawHitbox(Graphics g) {
        // для дебага
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, width, height); 
    }

    private void updateHitbox() {
        hitbox.x = (int) x - 130; 
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    // проверка коллизии - чтобы отпрыгивать от платформ
    public void checkCollision(List<entityPlatform> platforms) {
        // если падает
        if (velocity > 0) {
            for (entityPlatform p : platforms) {
                // если хитбоксы пересекаются
                if (this.hitbox.intersects(p.getHitbox())) {
                    // доп проверка
                    if (y + height <= p.getHitbox().y + p.getHitbox().height + 10) {
                        jump();
                        return;
                    }
                }
            }
        }
    }

}
