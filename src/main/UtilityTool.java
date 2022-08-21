package main;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;
import static main.GamePanel.WORLD_HEIGHT;
import static main.GamePanel.WORLD_WIDTH;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage ogImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, ogImage.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(ogImage, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public <T extends PositionKeeper> void adjustCamera(GamePanel gp, T t, int worldX, int worldY) {
        if(gp.player.worldX < gp.player.screenX) t.screenX = worldX;
        if(gp.player.worldY < gp.player.screenY) t.screenY = worldY;
        if(SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX) t.screenX = SCREEN_WIDTH - (WORLD_WIDTH - worldX);
        if(SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) t.screenY = SCREEN_HEIGHT - (WORLD_HEIGHT - worldY);
    }

    public Font setFont(float size) {
        try (InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf")) {
            assert is != null;
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void displayJVMSpecsUsage(Graphics2D g2) {

        /* Total number of processors or cores available to the JVM */
        g2.drawString("Available processors (cores): " +
                Runtime.getRuntime().availableProcessors(), 10, 430);

        /* Total amount of free memory available to the JVM */
        g2.drawString("Free memory (mbs): " +
                Runtime.getRuntime().freeMemory() / 1048576, 10, 460);

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
        /* Maximum amount of memory the JVM will attempt to use */
        g2.drawString("Maximum memory (mbs): " +
                (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory / 1048576), 10, 490);

        /* Total memory currently available to the JVM */
        g2.drawString("Total memory available to JVM (mbs): " +
                Runtime.getRuntime().totalMemory() / 1048576, 10, 520);
    }
}
