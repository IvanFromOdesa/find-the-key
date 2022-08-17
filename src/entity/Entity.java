package entity;

import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.ScreenPositionKeeper;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.*;

public abstract class Entity extends ScreenPositionKeeper {

    GamePanel gp;
    public int worldX, worldY;

    @Getter
    protected int speed;
    protected BufferedImage stand, up1, up2, down1, down2, left1, left2, right1, right2;

    @Getter
    protected String direction;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    @Getter
    protected Rectangle solidArea;

    @Getter
    @Setter
    protected boolean collisionOn = false;

    @Getter
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected String imgSpec;
    protected int actionLockCounter;
    BufferedImage image;

    UtilityTool uTool = new UtilityTool();

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    protected BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/" + imgSpec + imagePath +".png")));
            image = uTool.scaleImage(image, TILE_SIZE, TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // FOR NPCs
    protected void setAction() {

    }

    public void update() {
        setAction();
        collisionOn = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        defineCollision();
    }

    protected void defineCollision() {
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
        if(spriteCounter > 10) {
            if(spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        image = null;

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        // STOPPING THE CAMERA
        uTool.adjustCamera(gp, this, worldX, worldY);

        if (worldX + TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
            chooseDirection();
            g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
        // If player is around the edge, draw everything
        else if(gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) {
            chooseDirection();
            g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    private void chooseDirection() {
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
                if(spriteNum == 1) image = right1;
                if(spriteNum == 2) image = right2;
                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;
                break;
        }
    }
}
