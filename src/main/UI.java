package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font font;
    BufferedImage icon; // ui icon
    public boolean messageOn = false;
    public String message = "";
    int messageCount = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        font = new Font("MV Boli", Font.PLAIN, 40);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("Vita", 25, 50);

        // MESSAGE
        if(messageOn) {
            g2.setFont(g2.getFont().deriveFont(30f));
            g2.drawString(message, 24, 240);

            messageCount++;

            // THE TEXT DISAPPEARS AFTER 120 FRAMES (2 SECONDS)
            if(messageCount > 120) {
                messageCount = 0;
                messageOn = false;
            }
        }
    }
}
