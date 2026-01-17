package gamemobil;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Mobil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        // ⭐ TAMPILKAN MENU DULU
        frame.setContentPane(new MenuPanel(frame));
        frame.setVisible(true);
    }
}
