package main;

import lombok.Getter;
import lombok.Setter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;
import static main.GamePanel.TILE_SIZE;

public class UI {

    GamePanel gp;

    Graphics2D g2;
    Font font;

    @Getter
    @Setter
    private String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        font = new Font("MV Boli", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(font);
        g2.setColor(Color.WHITE);

        // PLAY STATE
        if(gp.gameState == gp.PLAY_STATE) {
            // Some stuff
        }
        // PAUSE STATE
        if(gp.gameState == gp.PAUSE_STATE) {
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.DIALOGUE_STATE) {
            drawDialogueScreen();
        }
    }

    private void drawDialogueScreen() {

        // WINDOW
        int x = TILE_SIZE * 2;
        int y = TILE_SIZE / 2;
        int width = SCREEN_WIDTH - (TILE_SIZE * 4);
        int height = TILE_SIZE * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f));
        x += TILE_SIZE;
        y += TILE_SIZE;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 30;
        }
    }

    private void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRoundRect(x, y, width ,height, 35, 35);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y +5, width -10, height -10, 25, 25);
    }


    private void drawPauseScreen() {

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
