package main;

import lombok.NoArgsConstructor;
import object.SuperObject;
import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GamePanel.*;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage ogImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, ogImage.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(ogImage, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public <T extends ScreenPositionKeeper> void adjustCamera(GamePanel gp, T t, int worldX, int worldY) {
        if(gp.player.worldX < gp.player.screenX) t.screenX = worldX;
        if(gp.player.worldY < gp.player.screenY) t.screenY = worldY;
        if(SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX) t.screenX = SCREEN_WIDTH - (WORLD_WIDTH - worldX);
        if(SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) t.screenY = SCREEN_HEIGHT - (WORLD_HEIGHT - worldY);
    }
}
