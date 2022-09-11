package entity;

import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.KeyHandler;
import object.weapon.projectile.PRJ_Pink_Fireball;
import object.weapon.projectile.Projectile;
import object.weapon.gun.WPN_Staff_Virtue;
import object.weapon.Weapon;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static main.GamePanel.*;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX, screenY;

    @Getter
    private int adjustedX, adjustedY;

    private BufferedImage right3, left3;
    private int spriteCounterSide = 0;
    private int spriteNumSide = 1;

    @Getter
    @Setter
    private boolean attacking;

    @Getter
    private Weapon gun;

    @Getter
    private Projectile ammo;

    public Player (GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        imgPath = "player/sprites/";
        width = TILE_SIZE;
        height = TILE_SIZE;

        screenX = SCREEN_WIDTH / 2 - TILE_SIZE / 2; // 360
        screenY = SCREEN_HEIGHT / 2 - TILE_SIZE / 2; // 264

        solidArea = new Rectangle(15, 36, 18, 12);
        solidAreaDefaultX = 15;
        solidAreaDefaultY = 36;

        setDefaultValues();
        getPlayerMoveImages();
        getPlayerAttackImages();
    }

    public void setDefaultValues() {
        // POSITION
        worldX = TILE_SIZE * 16;
        worldY = TILE_SIZE * 10;

        // SPEED
        speed = 4;
        direction = "stand";

        // LIFE
        maxLife = 100;
        currentLife = maxLife;

        // INVENTORY
        gun = new WPN_Staff_Virtue(gp);
        ammo = new PRJ_Pink_Fireball(gp, gun);
    }

    private void getPlayerMoveImages() {
        stand = setup("move/Vita");
        down1 = setup("move/Vita_down1");
        down2 = setup("move/Vita_down2");
        up1 = setup("move/Vita_up1");
        up2 = setup("move/Vita_up2");
        right1 = setup("move/Vita_right1");
        right2 = setup("move/Vita_right2");
        right3 = setup("move/Vita_right3");
        left1 = setup("move/Vita_left1");
        left2 = setup("move/Vita_left2");
        left3 = setup("move/Vita_left3");
    }

    private void getPlayerAttackImages() {
        attackDown = setup("attack/Vita_attack_down");
        attackUp = setup("attack/Vita_attack_up");
    }

    @Override
    public void update() {

        if(!attacking) {
            if(keyH.upPressed) direction = "up";
            else if(keyH.downPressed) direction = "down";
            else if(keyH.leftPressed) direction = "left";
            else if(keyH.rightPressed) direction = "right";
            else direction = "stand";
        }

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpPlayObject(objIndex);

        // CHECK NPC COLLISION
        gp.cChecker.checkEntity(this, gp.npc);

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        moveEntity();

        spriteCounterSide ++;

        // SIDE PLAYER's MOVEMENT
        if(spriteCounterSide > 10) {
            if(spriteNumSide == 1) spriteNumSide = 2;
            else if (spriteNumSide == 2) spriteNumSide = 3;
            else if (spriteNumSide == 3) spriteNumSide = 1;
            spriteCounterSide = 0;
        }

    }

    // FOR INTERACTIVE OBJECTS
    public void pickUpPlayObject(int i) {

    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "stand":
                if(!attacking) image = stand;
                else image = attackDown;
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

        adjustedX = x;
        adjustedY = y;

        if(!attacking) g2.drawImage(image, x, y, null);
        if(attacking) drawAttack(g2, image, x, y);

        // DISPLAY COLLISION
        /*g2.setColor(Color.RED);
        g2.drawRect(screenX + getSolidArea().x, screenY + getSolidArea().y,
                getSolidArea().width, getSolidArea().height);*/
    }

    private void drawAttack(Graphics2D g2, BufferedImage image, int x, int y) {
        g2.drawImage(image, x, y, null);
        g2.drawImage(gun.getWeaponImage(), x, y - 10, null);
    }
}
