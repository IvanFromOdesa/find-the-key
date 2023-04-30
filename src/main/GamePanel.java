package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import graphics.SFXAdjuster;
import object.SuperObject;
import object.weapon.projectile.Projectile;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public static final int MAX_WORLD_COLUMN = 42;
    public static final int MAX_WORLD_ROW = 25;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COLUMN;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    // FPS
    private final int FPS = 60;

    // SYSTEM
    Thread gameThread;
    public KeyHandler keyH = new KeyHandler(this);
    public TileManager tileM = new TileManager(this);
    MouseHandler mouseH = new MouseHandler(this);
    SoundHandler music = new SoundHandler();
    SoundHandler se = new SoundHandler();
    UtilityTool uTool = new UtilityTool();
    Font font = uTool.setFont(30f);
    public UI ui = new UI(this);
    public ScreenShaker shaker = new ScreenShaker(this);
    public SFXAdjuster SFXAdjuster = new SFXAdjuster(this);
    public PathFinder pf = new PathFinder(this);

    // COLLISION
    public CollisionChecker cChecker = new CollisionChecker(this);
    public EntityPlacer entPlacer = new EntityPlacer(this);

    // ENTITY
    public Player player = new Player(this, keyH, mouseH);
    public SuperObject[] objects = new SuperObject[100];
    public Entity[] entities = new Entity[10];
    public List<Projectile> projectiles = new ArrayList<>();
    public List<Entity> entToRemove = new ArrayList<>();
    List<PositionKeeper> entList = new ArrayList<>();

    private int timer;

    // GAME STATE
    public int gameState;
    public final  int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOGUE_STATE = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
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
        entPlacer.setObject();
        entPlacer.setEntities();
        //playMusic(0);
        gameState = PLAY_STATE;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if(gameState == PLAY_STATE || gameState == DIALOGUE_STATE) {
            // PLAYER
            player.update();

            // ENTITIES
            for(Entity entity : entities) {if(entity != null) entity.update();}
            if (!entToRemove.isEmpty()) {
                for (Entity ent: entToRemove) {
                    entities = UtilityTool.removeObjFromArray(entities, ent);
                }
                entToRemove.clear();
            }

            // PROJECTILES
            for(Projectile projectile : projectiles) {if(projectile != null) projectile.update();}
            projectiles.removeIf(prj -> !prj.isAlive());

            // RESETTING PRESSED BUTTONS
            timer++;
            if(timer == 120) {
                keyH.enterPressed = false;
                timer = 0;
            }
        }
        if(gameState == PAUSE_STATE) {
            // nothing
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime) drawStart = System.nanoTime();

        // TILES
        tileM.draw(g2);

        // ADDING PLAYER, NPC AND OBJECTS INTO ONE ENTITY LIST
        entList.add(player);

        for (Entity entity : entities) {if (entity != null) entList.add(entity);}

        for (SuperObject object : objects) {if (object != null) entList.add(object);}

        for(Projectile projectile : projectiles) {if (projectile != null) entList.add(projectile);}

        // SORTING ENTITIES BY THEIR POSITION
        entList.sort(Comparator.comparingInt(o -> o.worldY + o.height));

        // DRAW EVERYTHING
        for(PositionKeeper keeper : entList) {keeper.draw(g2);}
        entList.clear();

        // UI
        ui.draw(g2);

        // DEBUG
        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = (drawEnd - drawStart) / 1000000;
            g2.setColor(Color.WHITE);
            g2.setFont(font);
            g2.drawString("Draw Time: " + passed + " msc", 10, 400);
            g2.drawString("Player WX: " + player.worldX + ", WY: " + player.worldY, 10, 30);
            UtilityTool.displayJVMSpecsUsage(g2);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
