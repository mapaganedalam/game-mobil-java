package gamemobil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Player player;
    private ArrayList<Enemy> enemies;

    // 🏢 GEDUNG
    private ArrayList<Building> leftBuildings;
    private ArrayList<Building> rightBuildings;

    private Timer timer;
    private int score;

    // 🔊 SOUND
    private SoundPlayer bgSound;
    private SoundPlayer crashSound;

    // 🛣️ JALAN
    private static final int LANE_COUNT = 3;
    private int roadWidth;
    private int roadX;
    private int laneWidth;
    private int roadOffset = 0;

    // ⚡ SPEED SYSTEM
    private int baseSpeed = 6;
    private int gameSpeed = 6;

    // 🏢 GEDUNG CONFIG
    private static final int BUILDING_COUNT = 4;
    private static final int BUILDING_GAP_MIN = 260;
    private static final int BUILDING_GAP_MAX = 340;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        bgSound = new SoundPlayer();
        crashSound = new SoundPlayer();

        initGame();

        bgSound.loop("/assets/background.wav");

        timer = new Timer(16, this);
        timer.start();
    }

    private void initGame() {
        player = new Player(1, 450);

        enemies = new ArrayList<>();
        enemies.add(spawnEnemy());
        enemies.add(spawnEnemy());

        initBuildings();

        score = 0;
        gameSpeed = baseSpeed;
    }

    // =========================
    // 🏢 INIT GEDUNG
    // =========================
    private void initBuildings() {
        leftBuildings = new ArrayList<>();
        rightBuildings = new ArrayList<>();

        int y = -300;

        for (int i = 0; i < BUILDING_COUNT; i++) {
            int height = 140 + (int) (Math.random() * 120);

            leftBuildings.add(new Building(0, y, roadX, height));
            rightBuildings.add(new Building(roadX + roadWidth, y, roadX, height));

            y -= BUILDING_GAP_MIN +
                    (int) (Math.random() * (BUILDING_GAP_MAX - BUILDING_GAP_MIN));
        }
    }

    private Enemy spawnEnemy() {
        int lane = (int) (Math.random() * LANE_COUNT);
        int y = -150 - (int) (Math.random() * 300);
        return new Enemy(lane, y);
    }

    private void resetGame() {
        timer.stop();
        bgSound.stop();
        initGame();
        bgSound.loop("/assets/background.wav");
        timer.start();
    }

    // =========================
    // ⚡ SPEED NAIK TIAP 50 SCORE
    // =========================
    private void updateSpeed() {
        gameSpeed = baseSpeed + (score / 50);
    }

    // =========================
    // 🎨 RENDER
    // =========================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        updateLayout();

        drawBuildings(g2);
        drawRoad(g2);

        player.updatePosition(roadX, laneWidth);
        player.draw(g2);

        for (Enemy e : enemies) {
            e.updatePosition(roadX, laneWidth);
            e.draw(g2);
        }

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));
        g2.drawString("Score: " + score, 20, 30);
    }

    // =========================
    // 🏢 DRAW GEDUNG (AMAN RESIZE)
    // =========================
    private void drawBuildings(Graphics2D g) {

        int padding = 40;
        int maxWidth = 220;

        int leftWidth = Math.max(120, Math.min(roadX - padding, maxWidth));
        int rightWidth = Math.max(
                120,
                Math.min(getWidth() - (roadX + roadWidth) - padding, maxWidth)
        );

        for (Building b : leftBuildings) {
            b.setX(padding);
            b.setWidth(leftWidth);
            b.draw(g);
        }

        for (Building b : rightBuildings) {
            b.setX(roadX + roadWidth + padding);
            b.setWidth(rightWidth);
            b.draw(g);
        }
    }

    // =========================
    // 🔄 UPDATE LAYOUT
    // =========================
    private void updateLayout() {
        roadWidth = getWidth() * 3 / 5;
        roadX = (getWidth() - roadWidth) / 2;
        laneWidth = roadWidth / LANE_COUNT;
    }

    // =========================
    // 🛣️ JALAN
    // =========================
    private void drawRoad(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(roadX, 0, roadWidth, getHeight());

        g.setColor(Color.WHITE);
        for (int i = 1; i < LANE_COUNT; i++) {
            int x = roadX + i * laneWidth;
            for (int y = -40; y < getHeight(); y += 80) {
                g.fillRect(x - 3, y + roadOffset, 6, 40);
            }
        }
    }

    // =========================
    // 🔄 GAME LOOP
    // =========================
    @Override
    public void actionPerformed(ActionEvent e) {

        roadOffset += gameSpeed;
        if (roadOffset > 80) roadOffset = 0;

        updateBuildingList(leftBuildings);
        updateBuildingList(rightBuildings);

        for (Enemy enemy : enemies) {
            enemy.move(gameSpeed);

            if (enemy.getY() > getHeight()) {
                enemy.reset();
                score++;
                updateSpeed();
            }

            if (player.getBounds().intersects(enemy.getBounds())) {
                timer.stop();
                bgSound.stop();
                crashSound.play("/assets/crash.wav");

                int option = JOptionPane.showConfirmDialog(
                        this,
                        "GAME OVER\nScore: " + score + "\nRestart?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
                return;
            }
        }
        repaint();
    }

    // =========================
    // 🏢 GEDUNG BERGERAK HALUS
    // =========================
    private void updateBuildingList(ArrayList<Building> buildings) {

        for (Building b : buildings) {
            b.move(gameSpeed / 3);
        }

        Building last = buildings.get(buildings.size() - 1);

        if (buildings.get(0).getY() > getHeight()) {
            Building first = buildings.remove(0);

            int gap = BUILDING_GAP_MIN +
                    (int) (Math.random() * (BUILDING_GAP_MAX - BUILDING_GAP_MIN));

            first.reset(last.getY() - gap);
            buildings.add(first);
        }
    }

    // =========================
    // ⌨️ INPUT
    // =========================
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) player.moveLeftLane();
        if (e.getKeyCode() == KeyEvent.VK_D) player.moveRightLane();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
