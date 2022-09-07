package entity;

import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.PositionKeeper;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static main.GamePanel.*;

public abstract class Entity extends PositionKeeper {

    GamePanel gp;

    // ENTITY SPRITES
    protected BufferedImage stand, up1, up2, down1, down2, left1, left2, right1, right2;
    protected String imgPath;
    private BufferedImage image;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    // INFO
    protected String name;

    @Getter
    protected int maxLife;

    @Getter
    protected int currentLife;

    @Setter
    private boolean highlight;

    // MOVEMENT AREA
    protected int topBorder, bottomBorder, leftBorder, rightBorder;

    @Getter
    protected int speed;

    @Getter
    protected String direction;

    @Setter
    private boolean isStopped = false;

    // COLLISION
    @Getter
    protected Rectangle solidArea;

    @Getter
    @Setter
    protected boolean collisionOn = false;

    @Getter
    protected int solidAreaDefaultX, solidAreaDefaultY;
    private boolean collisionOnEntity = false;

    // COUNTER
    private int messageCounter, infoCounter;
    protected int actionLockCounter;

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

    public Entity(GamePanel gp, int width, int height, String direction, int speed,
                  int topBorder, int bottomBorder, int leftBorder, int rightBorder,
                  int solidAreaDefaultX, int solidAreaDefaultY,
                  Rectangle solidArea, String imgPath, String name) {
        this.gp = gp;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.speed = speed;
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.solidAreaDefaultX = solidAreaDefaultX;
        this.solidAreaDefaultY = solidAreaDefaultY;
        this.solidArea = solidArea;
        this.imgPath = imgPath;
        this.name = name;
    }

    protected BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/" + imgPath + imageName +".png")));
            image = uTool.scaleImage(image, width, height);
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

        if(this.worldX - gp.player.worldX >= -TILE_SIZE &&
                this.worldY - gp.player.worldY >= -TILE_SIZE &&
                this.worldX - gp.player.worldX <= TILE_SIZE &&
                this.worldY - gp.player.worldY <= TILE_SIZE &&
                gp.keyH.enterPressed) {
            startDialogue();
        }
    }

    private void startDialogue() {
        gp.gameState = gp.DIALOGUE_STATE;
        this.speak();
        gp.keyH.enterPressed = false;
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

    @Override
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
            g2.drawImage(image, screenX, screenY, null);

            // DISPLAY COLLISION
            /*g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,
                    solidArea.width, solidArea.height);*/

            if(collisionOnEntity) displayQuotes(g2);
            if(highlight) showInfo(g2);
        }
        // If player is around the edge, draw everything
        else if(gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) {
            chooseDirection();
            g2.drawImage(image, screenX, screenY, null);

            if(collisionOnEntity) displayQuotes(g2);
            if(highlight) showInfo(g2);
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

    protected void showInfo(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString(name, (int) ((this.screenX + 24) -
                (g2.getFontMetrics().getStringBounds(name, g2).getWidth()) / 2), this.screenY - 20);
        infoCounter ++;

        if(infoCounter == 240) {
            highlight = false;
            infoCounter = 0;
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
