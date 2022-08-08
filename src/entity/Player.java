package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static main.GamePanel.TILE_SIZE;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "up";
    }

    public void getPlayerImage() {
        try {
            stand = ImageIO.read(getClass().getResourceAsStream("/player/Vita.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed) {
            direction = "up";
            y -= speed;
        } else if(keyH.downPressed) {
            direction = "down";
            y += speed;
        } else if(keyH.leftPressed) {
            direction = "left";
            x -= speed;
        } else if(keyH.rightPressed) {
            direction = "right";
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = stand;
        }

        g2.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
    }
}
