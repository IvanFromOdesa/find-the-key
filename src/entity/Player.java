package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    private BufferedImage right3, left3;
    private int spriteCounterSide = 0;
    private int spriteNumSide = 1;


    public Player (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = SCREEN_WIDTH / 2 - TILE_SIZE / 2;
        screenY = SCREEN_HEIGHT / 2 - TILE_SIZE / 2;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = TILE_SIZE * 16;
        worldY = TILE_SIZE * 10;
        speed = 4;
        direction = "stand";
    }

    public void getPlayerImage() {
        try {
            stand = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_down2.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_up2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_right2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_right3.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_left2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Vita_left3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed) {
            direction = "up";
            worldY -= speed;
        } else if(keyH.downPressed) {
            direction = "down";
            worldY += speed;
        } else if(keyH.leftPressed) {
            direction = "left";
            worldX -= speed;
        } else if(keyH.rightPressed) {
            direction = "right";
            worldX += speed;
        } else {
            direction = "stand";
        }

        spriteCounter++;
        spriteCounterSide ++;
        if(spriteCounter > 10) {
            if(spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // SIDE PLAYER's MOVEMENT
        if(spriteCounterSide > 10) {
            if(spriteNumSide == 1) {
                spriteNumSide = 2;
            } else if (spriteNumSide == 2) {
                spriteNumSide = 3;
            } else if (spriteNumSide == 3) {
                spriteNumSide = 1;
            }
            spriteCounterSide = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "stand":
                image = stand;
                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                break;
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                break;
            case "right":
                if(spriteNumSide == 1) image = right1;
                if(spriteNumSide == 2) image = right2;
                if(spriteNumSide == 3) image = right3;
                break;
            case "left":
                if(spriteNumSide == 1) image = left1;
                if(spriteNumSide == 2) image = left2;
                if(spriteNumSide == 3) image = left3;
                break;
        }

        g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
    }
}
