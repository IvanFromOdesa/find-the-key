import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class GamePanel extends JPanel /*implements Runnable*/ {

    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16;
    final int SCALE = 3;

    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    final int MAX_SCREEN_COLUMN = 16;
    final int MAX_SCREEN_ROW = 12;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    // PLAYER'S LOCATION
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // FPS
    int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    /*@Override
    public void run() {

    }*/
}
