package gamemobil;

import java.awt.*;

public abstract class GameObject {

    protected int x, y;
    protected int speed;

    // ukuran GAMBAR
    protected int width, height;

    // ukuran HITBOX (INI YANG DIPAKAI TABRAKAN)
    protected int hitboxWidth = 30;
    protected int hitboxHeight = 50;

    public Rectangle getBounds() {
        int hitboxX = x + (width - hitboxWidth) / 2;
        int hitboxY = y + (height - hitboxHeight) / 2;

        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public int getY() {
        return y;
    }

    public abstract void draw(Graphics g);
}
