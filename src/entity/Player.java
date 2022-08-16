package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

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

        solidArea = new Rectangle(15, 36, 18, 12);
        solidAreaDefaultX = 15;
        solidAreaDefaultY = 36;

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
        stand = setup("Vita");
        down1 = setup("Vita_down1");
        down2 = setup("Vita_down2");
        up1 = setup("Vita_up1");
        up2 = setup("Vita_up2");
        right1 = setup("Vita_right1");
        right2 = setup("Vita_right2");
        right3 = setup("Vita_right3");
        left1 = setup("Vita_left1");
        left2 = setup("Vita_left2");
        left3 = setup("Vita_left3");
    }

    public BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/player/" + imagePath +".png")));
            image = uTool.scaleImage(image, TILE_SIZE, TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void update() {
        if(keyH.upPressed) {
            direction = "up";
        } else if(keyH.downPressed) {
            direction = "down";
        } else if(keyH.leftPressed) {
            direction = "left";
        } else if(keyH.rightPressed) {
            direction = "right";
        } else {
            direction = "stand";
        }

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpPlayObject(objIndex);

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(!collisionOn) {
            switch (direction) {
                case "stand": break;
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "right": worldX += speed; break;
                case "left": worldX -= speed; break;
            }
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

    // FOR INTERACTIVE OBJECTS
    public void pickUpPlayObject(int i) {

        if(i != -1) {
            String name = gp.objects[i].getName();
            switch (name) {
                case "Tree_1":
                    gp.ui.showMessage("You hit a tree #1!");
                    break;
                case "Tree_2":
                    gp.ui.showMessage("You hit a tree #2!");
                    break;
                case "Bush_1":
                    gp.ui.showMessage("You hit a bush #1!");
                    break;
                case "Bush_2":
                    gp.ui.showMessage("You hit a bush #2!");
                    break;
            }
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

        int x = screenX;
        int y = screenY;

        if(screenX > worldX) x = worldX;
        if(screenY > worldY) y = worldY;
        if(SCREEN_WIDTH - screenX > WORLD_WIDTH - worldX) x = SCREEN_WIDTH - (WORLD_WIDTH - worldX);
        if(SCREEN_HEIGHT - screenY > WORLD_HEIGHT - worldY) y = SCREEN_HEIGHT - (WORLD_HEIGHT - worldY);

        g2.drawImage(image, x, y, null);

        // DISPLAY COLLISION
        /*g2.setColor(Color.RED);
        g2.drawRect(screenX + getSolidArea().x, screenY + getSolidArea().y,
                getSolidArea().width, getSolidArea().height);*/
    }
}
