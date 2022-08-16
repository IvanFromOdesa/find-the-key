package object;

import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.ScreenPositionKeeper;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GamePanel.*;

public class SuperObject extends ScreenPositionKeeper {

    protected BufferedImage image;

    @Getter
    protected String name;

    @Getter
    protected boolean collision = false;

    // OBJECT's REAL WIDTH AND HEIGHT
    protected int width, height;

    // OBJECT's WIDTH AND HEIGHT
    protected int scaleX, scaleY;
    public int worldX, worldY;

    public Rectangle solidArea;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp) {

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        // STOPPING THE CAMERA
        uTool.adjustCamera(gp, this, worldX, worldY);

        if (worldX + TILE_SIZE * scaleX > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE * scaleY > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, width, height, null);

            // DISPLAY COLLISION
            /*g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,
                    solidArea.width, solidArea.height);*/
        }
        else if(gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }
}
