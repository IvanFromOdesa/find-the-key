package object;

import lombok.Getter;
import main.GamePanel;
import main.PositionKeeper;
import main.UtilityTool;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
    protected boolean collision = false;

    // OBJECT's WIDTH AND HEIGHT
    protected int scaleX, scaleY;

    public Rectangle solidArea;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    UtilityTool uTool = new UtilityTool();
    GamePanel gp;

    public SuperObject(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void draw(Graphics2D g2) {

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
