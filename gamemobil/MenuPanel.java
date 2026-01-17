package gamemobil;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(JFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel title = new JLabel("GAME MOBIL");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JButton start = new JButton("START");
        JButton exit  = new JButton("EXIT");

        start.setPreferredSize(new Dimension(200, 50));
        exit.setPreferredSize(new Dimension(200, 50));

        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy = 1;
        add(start, gbc);

        gbc.gridy = 2;
        add(exit, gbc);

        start.addActionListener(e -> {
            GamePanel game = new GamePanel();
            frame.setContentPane(game);
            frame.revalidate();
            game.requestFocusInWindow(); // ⌨️ A & D AKTIF
        });

        exit.addActionListener(e -> System.exit(0));
    }
}
