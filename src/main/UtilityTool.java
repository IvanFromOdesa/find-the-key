package main;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GamePanel.TILE_SIZE;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage ogImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, ogImage.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(ogImage, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
