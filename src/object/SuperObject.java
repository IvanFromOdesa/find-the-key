package object;

import lombok.Getter;
import main.GamePanel;
import main.PositionKeeper;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;
import static main.GamePanel.TILE_SIZE;
import static main.GamePanel.WORLD_HEIGHT;
import static main.GamePanel.WORLD_WIDTH;

public class SuperObject extends PositionKeeper {

    protected BufferedImage image;

    @Getter
    protected String name;

    @Getter
    protected boolean collision;

    // OBJECT's WIDTH AND HEIGHT
    protected int scaleX, scaleY;

    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    protected GamePanel gp;

    protected SuperObject(GamePanel gp, boolean collision,
                       int scaleX, int scaleY, int width, int height,
                       int solidAreaDefaultX, int solidAreaDefaultY,
                       Rectangle solidArea, String imagePath) {
        this.gp = gp;
        this.collision = collision;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.width = width;
        this.height = height;
        this.solidAreaDefaultX = solidAreaDefaultX;
        this.solidAreaDefaultY = solidAreaDefaultY;
        this.solidArea = solidArea;

        loadImage(imagePath);
    }

    protected SuperObject(GamePanel gp, int width, int height,
                       String imagePath) {
        this.gp = gp;
        this.width = width;
        this.height = height;

        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            image = UtilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        // STOPPING THE CAMERA
        UtilityTool.adjustCamera(gp, this, worldX, worldY);

        if (worldX + TILE_SIZE * scaleX > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE * scaleY > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, null);

            // DISPLAY COLLISION
            /*g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,
                    solidArea.width, solidArea.height);*/
        }
        else if(gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) {
            g2.drawImage(image, screenX, screenY, null);
        }
    }
}
