package entity;

import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.PositionKeeper;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;
import static main.GamePanel.TILE_SIZE;
import static main.GamePanel.WORLD_HEIGHT;
import static main.GamePanel.WORLD_WIDTH;

public abstract class Entity extends PositionKeeper {

    GamePanel gp;

    @Getter
    protected int speed;
    protected BufferedImage stand, up1, up2, down1, down2, left1, left2, right1, right2;

    @Getter
    protected String direction;

    @Setter
    private boolean isStopped = false;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    @Getter
    protected Rectangle solidArea;

    @Getter
    @Setter
    protected boolean collisionOn = false;

    @Getter
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected String imgPath;
    protected int actionLockCounter;
    protected int topBorder, bottomBorder, leftBorder, rightBorder;
    private BufferedImage image;
    private int messageCounter = 0;

    private boolean collisionOnEntity = false;
    private String quote;

    UtilityTool uTool = new UtilityTool();
    Random random = new Random();
    Font font = uTool.setFont(20f);

    public final String[] QUOTES = {"Sorry!", "I'm sorry!", "Watch where you going",
                                    "You blind?", "I'm really sorry for that",
                                    "I keep my eye on you",
                                    "Oh, that hurts...", "Oops..."};

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    protected BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/" + imgPath + imageName +".png")));
            image = uTool.scaleImage(image, TILE_SIZE, TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // FOR NPCs
    protected void setAction() {

    }

    /**
     * This method is used for dialogues.
     */
    public void speak() {

    }

    protected final void setDialogueBehaviour(String[] dialogues) {

        gp.ui.setCurrentDialogue(dialogues[new Random().nextInt(dialogues.length)]);

        this.direction = "stand";
        this.isStopped = true;
    }

    public void update() {

        if(gp.gameState == gp.PLAY_STATE) this.isStopped = false;

        if(!isStopped) setAction();
        collisionOn = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.npc);

        if(!isStopped) moveEntity();
        checkForDialogue();

    }

    private void checkForDialogue() {
        if(this.worldX - gp.player.worldX >= -TILE_SIZE && this.worldY - gp.player.worldY >= -TILE_SIZE &&
                this.worldX - gp.player.worldX <= TILE_SIZE && this.worldY - gp.player.worldY <= TILE_SIZE &&
                gp.keyH.enterPressed) {
            gp.gameState = gp.DIALOGUE_STATE;
            this.speak();

            gp.keyH.enterPressed = false;
        }
    }

    protected void moveEntity() {
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

    // CHECKS IF THE NPC OR MONSTER CAN MOVE TO CERTAIN LOCATIONS

    private void checkMovementAvailability() {
        if(this.worldY < topBorder) direction = "down";
        else if(this.worldY > bottomBorder) direction = "up";
        else if(this.worldX < leftBorder) direction = "right";
        else if(this.worldX > rightBorder) direction = "left";
    }

    protected final void basicAI(int framesNum) {
        if (actionLockCounter == framesNum) {
            Random random = new Random();
            int choice = random.nextInt(140) + 1;

            if (choice <= 25) {
                direction = "up";
                checkMovementAvailability();
            }
            if (choice > 25 && choice <= 50) {
                direction = "down";
                checkMovementAvailability();
            }
            if (choice > 50 && choice <= 75) {
                direction = "left";
                checkMovementAvailability();
            }
            if (choice > 75 && choice <= 100) {
                direction = "right";
                checkMovementAvailability();
            }
            if (choice > 100 && choice <= 140) {
                direction = "stand";
                checkMovementAvailability();
            }
            actionLockCounter = 0;
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

            if(collisionOnEntity) displayQuotes(g2);
        }
        // If player is around the edge, draw everything
        else if(gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) {
            chooseDirection();
            g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);

            if(collisionOnEntity) displayQuotes(g2);
        }
    }

    protected void displayQuotes(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(Color.WHITE);

        g2.drawString(quote, (int) ((this.screenX + 24) -
                (g2.getFontMetrics().getStringBounds(quote, g2).getWidth()) / 2), this.screenY - 5);
        messageCounter++;

        if(messageCounter == 120){
            collisionOnEntity = false;
            messageCounter = 0;
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

    public void setCollisionOnEntity(boolean collisionOnEntity) {
        this.collisionOnEntity = collisionOnEntity;
        if(messageCounter == 0) quote = this.QUOTES[random.nextInt(QUOTES.length)];
    }
}
