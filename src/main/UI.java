package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font font;
    //BufferedImage icon; // ui icon
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
        /*g2.setFont(font);
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
        }*/

        this.g2 = g2;

        g2.setFont(font);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.playState) {
            // Some stuff
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70));

        String text = "PAUSED";
        int x = getXPosForTextCentered(text);
        int y = SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);
    }

    private int getXPosForTextCentered(String text) {
        return SCREEN_WIDTH / 2 - ((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
    }
}
