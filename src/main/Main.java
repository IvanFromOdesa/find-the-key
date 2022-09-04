package main;

import javax.swing.JFrame;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame();
        window.setTitle("Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("res\\cursors\\cursor.png");
        Cursor cursor = toolkit.createCustomCursor(img, new Point(0, 0), "FCursor");
        window.setCursor(cursor);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}