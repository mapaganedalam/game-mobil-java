package gamemobil;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends GameObject {

    private BufferedImage image;
    private int lane;

    public Player(int lane, int y) {
        this.lane = lane;
        this.y = y;
        this.width = 50;
        this.height = 90;

        try {
            image = ImageIO.read(getClass().getResource("/assets/player.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔁 DIPANGGIL DARI GamePanel SETIAP FRAME
    public void updatePosition(int roadX, int laneWidth) {
        this.x = roadX
                + lane * laneWidth
                + laneWidth / 2
                - width / 2;
    }

    public void moveLeftLane() {
        if (lane > 0) {
            lane--;
        }
    }

    public void moveRightLane() {
        if (lane < 2) { // 3 lane
            lane++;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
}
