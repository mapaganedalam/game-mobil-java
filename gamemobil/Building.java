package gamemobil;

import java.awt.*;

public class Building {

    private int x, y;
    private int width, height;

    public Building(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // =====================
    // SETTER
    // =====================
    public void setX(int x) {
        this.x = x;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    // =====================
    // LOGIC
    // =====================
    public void move(int speed) {
        y += speed;
    }

    public int getY() {
        return y;
    }

    public void reset(int newY) {
        y = newY;
    }

    // =====================
    // DRAW
    // =====================
    public void draw(Graphics2D g) {

        // Gedung utama
        g.setColor(new Color(35, 35, 35));
        g.fillRect(x, y, width, height);

        // 🔥 SEMUA LAMPU NYALA
        g.setColor(new Color(240, 230, 120));

        for (int wx = x + 12; wx < x + width - 12; wx += 24) {
            for (int wy = y + 14; wy < y + height - 14; wy += 28) {
                g.fillRect(wx, wy, 14, 18);
            }
        }
    }
}
