package object;

import lombok.Getter;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GamePanel.TILE_SIZE;

public class SuperObject {

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

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + TILE_SIZE * scaleX > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE * scaleY > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }
}
