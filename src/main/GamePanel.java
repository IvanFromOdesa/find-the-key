package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 3;

    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COLUMN = 16;

    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    // WORLD SETTINGS
    public static final int MAX_WORLD_COLUMN = 40;
    public static final int MAX_WORLD_ROW = 24;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COLUMN;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;


    // FPS
    int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    TileManager tileM = new TileManager(this);

    public ObjectSetter objSetter = new ObjectSetter(this);
    public Player player = new Player(this, keyH);

    // 10 slots for objects. 10 for simultaneous spawning.
    public SuperObject[] objects = new SuperObject[100];

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // GAME LOOP
    @Override
    public void run() {

        double drawInterval = 1000000000.0/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1) {
                // 1 UPDATE: update information such as character position
                update();

                // 2 DRAW: draw the screen with the updated information
                repaint();

                delta--;
            }
        }
    }

    // SETTING UP OBJECTS, NPC etc.
    public void setUpGame() {
        objSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILES
        tileM.draw(g2);

        // OBJECTS
        Arrays.stream(objects).forEach(obj -> {
            if(obj != null) {
                obj.draw(g2, this);
            }
        });

        // PLAYER
        player.draw(g2);
        g2.dispose();
    }
}
