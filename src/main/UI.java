package main;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;
import static main.GamePanel.TILE_SIZE;

public class UI {

    GamePanel gp;

    Graphics2D g2;
    Font maruMonica;

    // HUD
    BufferedImage healthBar;
    BufferedImage health;
    BufferedImage heart;

    UtilityTool uTool = new UtilityTool();

    @Getter
    @Setter
    private String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        maruMonica = uTool.setFont(40f);
        loadHUD();
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        // PLAY STATE
        if(gp.gameState == gp.PLAY_STATE) {
            drawHUD();
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

    private void drawHUD() {

        double oneScale = (double) TILE_SIZE * 4 / gp.player.getMaxLife();
        double hpBarValue = oneScale * gp.player.getCurrentLife();

        g2.drawImage(healthBar, gp.player.screenX - 340, gp.player.screenY - 250, null);
        g2.drawImage(health, gp.player.screenX - 340, gp.player.screenY - 250,
                (int) hpBarValue, TILE_SIZE, null);
        g2.drawImage(heart, gp.player.screenX - 361, gp.player.screenY - 262, null);
    }

    private void loadHUD() {
        try {
            healthBar = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/hud/health_bar.png")));
            healthBar = uTool.scaleImage(healthBar, TILE_SIZE * 4, TILE_SIZE);

            health = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/hud/health.png")));
            health = uTool.scaleImage(health, TILE_SIZE * 4, TILE_SIZE);

            heart = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/hud/heart.png")));
            heart = uTool.scaleImage(heart, 72, 72);
        } catch (IOException e) {
            e.printStackTrace();
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
