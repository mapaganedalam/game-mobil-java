package gamemobil;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

public class Enemy extends GameObject {

    private BufferedImage image;
    private int lane;
    private Random random = new Random();

    public Enemy(int lane, int y) {
        this.lane = lane;
        this.y = y;
        this.width = 50;
        this.height = 90;

        try {
            image = ImageIO.read(getClass().getResource("/assets/enemy.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Posisi lane
    public void updatePosition(int roadX, int laneWidth) {
        this.x = roadX
                + lane * laneWidth
                + laneWidth / 2
                - width / 2;
    }

    // 🚗 GERAK IKUT GAME SPEED
    public void move(int gameSpeed) {
        y += gameSpeed;
    }

    // Reset enemy
    public void reset() {
        lane = random.nextInt(3);
        y = -height - random.nextInt(300);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
}
