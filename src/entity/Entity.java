package entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.PositionKeeper;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;
import static main.GamePanel.TILE_SIZE;
import static main.GamePanel.WORLD_HEIGHT;
import static main.GamePanel.WORLD_WIDTH;

@EqualsAndHashCode(callSuper = true)
public abstract class Entity extends PositionKeeper {

    protected GamePanel gp;

    // ENTITY SPRITES
    protected BufferedImage stand, up1, up2, down1, down2, left1, left2, right1, right2;
    protected BufferedImage attackUp, attackDown;
    protected Image dieImg;
    protected String imgPath;
    protected BufferedImage image;
    protected int spriteNum = 1;

    // INFO
    protected String name;
    protected int previousPosX, previousPosY;
    @Getter
    protected int maxLife;
    @Getter
    @Setter
    protected int currentLife;
    protected boolean dying;
    @Setter
    protected boolean damaged;
    @Getter
    protected int damage;
    @Getter
    protected int knockback;
    @Getter
    protected boolean stunned;
    protected String stunDirection;
    @Setter
    private boolean highlight;

    // MOVEMENT AREA
    protected int topBorder, bottomBorder, leftBorder, rightBorder;

    @Getter
    protected int speed;
    protected int initSpeed;
    @Getter
    protected String direction;
    @Setter
    protected boolean stopped;

    // COLLISION
    @Getter
    protected Rectangle solidArea;
    @Setter
    protected boolean collisionOn;
    @Getter
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected boolean collisionOnEntity;
    private boolean bumpedIn;
    // COUNTER
    private int messageCounter, infoCounter;
    protected int actionLockCounter;
    protected int spriteCounter;
    protected int stunCounter;
    protected int dieCounter;
    protected int collisionCounter;
    @Getter
    protected boolean npc;
    @Getter
    protected boolean enemy;
    @Getter
    protected boolean player;
    // If the entity is in "free roam mode"
    protected boolean wandering;
    protected boolean onPath;
    protected boolean notDefaultSpritesNum;
    protected boolean SFXPresent;
    protected int sfxAdjustScreenX, sfxAdjustScreenY;
    private String quote;
    protected Random random = new Random();
    Font font = new UtilityTool().setFont(20f);

    public final String[] QUOTES = {"Sorry!", "I'm sorry!", "Watch where you going",
                                    "You blind?", "I'm really sorry for that",
                                    "I keep my eye on you",
                                    "Oh, that hurts...", "Oops..."};

    protected Entity(GamePanel gp) {
        this.gp = gp;
    }

    protected Entity(GamePanel gp, int width, int height, String direction, int speed,
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
        initSpeed = speed;
    }

    protected BufferedImage setup(String imageName) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/" + imgPath + imageName +".png")));
            image = UtilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // For movement
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
        this.stopped = true;
    }

    public void update() {
        if (!stunned) {
            speed = initSpeed;
            if (gp.gameState == gp.PLAY_STATE) this.stopped = false;

            if (!stopped) setAction();
            checkCollision();
            if (!stopped) moveEntity();

            if (npc) {
                if (this.worldX - gp.player.worldX >= -TILE_SIZE &&
                        this.worldY - gp.player.worldY >= -TILE_SIZE &&
                        this.worldX - gp.player.worldX <= TILE_SIZE &&
                        this.worldY - gp.player.worldY <= TILE_SIZE &&
                        gp.keyH.enterPressed) {
                    startDialogue();
                }
                if (currentLife <= 0) gp.entToRemove.add(this);
            }
            else if (enemy) {
                // TODO: set enemy
            }
        } else {
            moveOnStun();
        }
    }

    protected void checkCollision() {
        collisionOn = false;
        collisionOnEntity = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.entities);
        gp.cChecker.checkProjectiles(this);
    }

    protected void moveOnStun() {
        // Move smoothly by 1 pixel each frame
        speed = 1;
        checkCollision();
        if (!collisionOn) changeCoords(stunDirection, this);
        checkResetPosition();
        stunCounter --;
        if (stunCounter <= 0) {
            stunned = false;
        }
    }

    protected void checkResetPosition() {
        if (!collisionOn) {
            previousPosX = worldX;
            previousPosY = worldY;
        }
        else {
            collisionCounter  ++;

            if (collisionCounter == 64) {
                collisionCounter = 0;
                this.worldX = previousPosX;
                this.worldY = previousPosY;
            }
        }
    }

    private void startDialogue() {
        gp.gameState = gp.DIALOGUE_STATE;
        this.speak();
        gp.keyH.enterPressed = false;
    }

    protected void moveEntity() {
        checkResetPosition();
        changeCoords(speed);

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

    protected void changeCoords(int speed) {
        if (!collisionOn && !collisionOnEntity) {
            switch (direction) {
                case "stand": break;
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "right": worldX += speed; break;
                case "left": worldX -= speed; break;
            }
        }
    }

    protected void changeCoords(String stunDirection, Entity targetEntity) {
        if (stunned) {
            switch (targetEntity.direction) {
                case "stand":
                    // If target stands and enemy is attacking
                    switch (stunDirection) {
                        case "stand": break;
                        case "up": targetEntity.worldY -= 1; break;
                        case "down": targetEntity.worldY += 1; break;
                        case "right": targetEntity.worldX += 1; break;
                        case "left": targetEntity.worldX -= 1; break;
                    } break;
                // Else push target opposite of its direction
                case "up": targetEntity.worldY += 1; break;
                case "down": targetEntity.worldY -= 1; break;
                case "right": targetEntity.worldX -= 1; break;
                case "left": targetEntity.worldX += 1; break;
            }
        }
    }

    // CHECKS IF THE NPC OR ENEMY CAN MOVE TO CERTAIN LOCATIONS

    private void checkMovementAvailability() {
        if(this.worldY < topBorder) direction = "down";
        else if(this.worldY > bottomBorder) direction = "up";
        else if(this.worldX < leftBorder) direction = "right";
        else if(this.worldX > rightBorder) direction = "left";
    }

    // Randomly changes directions of entity
    protected final void basicAI(int framesNum) {
        if (actionLockCounter == framesNum) {
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
            if (choice > 100) {
                direction = "stand";
                checkMovementAvailability();
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        // STOPPING THE CAMERA
        UtilityTool.adjustCamera(gp, this, worldX, worldY);

        if (worldX + TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                worldX - TILE_SIZE < gp.player.worldX  + gp.player.screenX &&
                worldY + TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
            drawEverything(g2);
        }
        // If player is around the edge, draw everything
        else if(gp.player.worldX < gp.player.screenX ||
                gp.player.worldY < gp.player.screenY ||
                SCREEN_WIDTH - gp.player.screenX > WORLD_WIDTH - gp.player.worldX ||
                SCREEN_HEIGHT - gp.player.screenY > WORLD_HEIGHT - gp.player.worldY) {
            drawEverything(g2);
        }
    }

    private void drawEverything(Graphics2D g2) {
        if (!notDefaultSpritesNum) chooseDirection();
        if (!SFXPresent) {
            gp.SFXAdjuster.drawShadow(g2, screenX, screenY, sfxAdjustScreenX, sfxAdjustScreenY);
        }
        g2.drawImage(image, screenX, screenY, null);

        if(npc && bumpedIn) displayQuotes(g2);
        if(highlight) showInfo(g2);
        // DISPLAY COLLISION
        /*g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,
                solidArea.width, solidArea.height);*/
    }

    protected void displayQuotes(Graphics2D g2) {
        setUpScreenText(g2, Color.WHITE, quote, this.screenY - 5);
        messageCounter++;

        if (messageCounter == 120) {
            bumpedIn = false;
            messageCounter = 0;
        }
    }

    protected void showInfo(Graphics2D g2) {
        setUpScreenText(g2, Color.BLACK, name + " " + currentLife + "/" + maxLife,
                this.screenY - 20);
        infoCounter ++;

        if(infoCounter == 240) {
            highlight = false;
            infoCounter = 0;
        }
    }

    private void setUpScreenText(Graphics2D g2, Color textColor, String text, int vertPos) {
        g2.setFont(font);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16f));
        g2.setColor(textColor);

        g2.drawString(text, (int) ((this.screenX + 24) -
                (g2.getFontMetrics().getStringBounds(text, g2).getWidth()) / 2), vertPos);
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

    public void setCollisionOnEntity(boolean collisionOnEntity, Entity targetEntity) {
        this.collisionOnEntity = collisionOnEntity;
        this.bumpedIn = true;
        targetEntity.collisionOnEntity = collisionOnEntity;
        if (npc) {
            if (messageCounter == 0) quote = this.QUOTES[random.nextInt(QUOTES.length)];
        }
        if (enemy) {
            if (targetEntity.isNpc() || targetEntity.isPlayer()) {
                if (!targetEntity.stunned) this.setAttacking(targetEntity);
            }
        } else {
            if (targetEntity.isEnemy()) {
                if (!this.stunned) targetEntity.setAttacking(this);
            }
        }
    }

    private void setAttacking(Entity targetEntity) {
        targetEntity.setCurrentLife(targetEntity.getCurrentLife() - this.getDamage());
        targetEntity.stunned = true;
        targetEntity.stunCounter = this.knockback;
        targetEntity.stunDirection = this.direction;
    }

    protected void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / TILE_SIZE;
        int startRow = (worldY + solidArea.y) / TILE_SIZE;

        gp.pf.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pf.search()) {
            // Next worldX & worldY
            int nextX = gp.pf.getPathList().get(0).getCol() * TILE_SIZE;
            int nextY = gp.pf.getPathList().get(0).getRow() * TILE_SIZE;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE) {
                direction = "up";
            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE) {
                direction = "down";
            }
            else if (enTopY >= nextY && enBottomY < nextY + TILE_SIZE) {
                if (enLeftX > nextX) direction = "left";
                if (enLeftX < nextX) direction = "right";
            }
            else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn || collisionOnEntity) {
                    direction = "left";
                }
            }
            else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn || collisionOnEntity) {
                    direction = "right";
                }
            }
            else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn || collisionOnEntity) {
                    direction = "left";
                }
            }
            else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn || collisionOnEntity) {
                    direction = "right";
                }
            }

            // Stop if goal reached
            /*int nextCol = gp.pf.getPathList().get(0).getCol();
            int nextRow = gp.pf.getPathList().get(0).getRow();

            if (nextCol == goalCol && nextRow == goalRow) onPath = false;*/
        }
    }
}
