package entity;

import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import object.weapon.projectile.PRJ_Pink_Fireball;
import object.weapon.projectile.Projectile;
import object.weapon.gun.WPN_Staff_Virtue;
import object.weapon.Weapon;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;
import static main.GamePanel.TILE_SIZE;
import static main.GamePanel.WORLD_HEIGHT;
import static main.GamePanel.WORLD_WIDTH;

public class Player extends Entity {

    KeyHandler keyH;
    MouseHandler mouseH;

    public final int screenX, screenY;

    @Getter
    private int adjustedX, adjustedY;

    private BufferedImage right3, left3;
    private int spriteCounterSide = 0;
    private int spriteNumSide = 1;

    @Getter
    @Setter
    private boolean attacking;

    @Setter
    private MouseEvent e;

    @Getter
    private Weapon weapon;

    @Getter
    private Projectile ammo;

    public Player (GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {

        super(gp);

        this.keyH = keyH;
        this.mouseH = mouseH;

        imgPath = "player/sprites/";
        width = TILE_SIZE;
        height = TILE_SIZE;

        screenX = SCREEN_WIDTH / 2 - TILE_SIZE / 2; // 360
        screenY = SCREEN_HEIGHT / 2 - TILE_SIZE / 2; // 264

        solidArea = new Rectangle(15, 36, 18, 12);
        solidAreaDefaultX = 15;
        solidAreaDefaultY = 36;

        sfxAdjustScreenX = 4;
        sfxAdjustScreenY = 38;

        player = true;

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
        // Default speed value. Speed can be changed if stunned etc.
        initSpeed = speed;
        direction = "stand";

        // LIFE
        maxLife = 100;
        currentLife = maxLife;

        // INVENTORY
        weapon = new WPN_Staff_Virtue(gp);
        ammo = new PRJ_Pink_Fireball(gp, weapon);
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
        if (!stunned) {
            gp.shaker.resetScreenPosition();
            speed = initSpeed;
            if (!attacking) {
                if(keyH.upPressed) direction = "up";
                else if(keyH.downPressed) direction = "down";
                else if(keyH.leftPressed) direction = "left";
                else if(keyH.rightPressed) direction = "right";
                else direction = "stand";
            }
            checkCollision();
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            moveEntity();

            spriteCounterSide ++;

            // SIDE PLAYER's MOVEMENT
            if (spriteCounterSide > 10) {
                if(spriteNumSide == 1) spriteNumSide = 2;
                else if (spriteNumSide == 2) spriteNumSide = 3;
                else if (spriteNumSide == 3) spriteNumSide = 1;
                spriteCounterSide = 0;
            }

            if (!ammo.isAlive() && mouseH.lmbPressed && attacking) setAttack(e);
        } else {
            moveOnStun();
            gp.shaker.shake();
        }
    }

    @Override
    protected void checkCollision() {
        // CHECK TILE COLLISION
        collisionOn = false;
        collisionOnEntity = false;
        gp.cChecker.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpPlayObject(objIndex);

        // CHECK NPC OR ENEMY COLLISION
        gp.cChecker.checkEntity(this, gp.entities);
        gp.cChecker.checkProjectiles(this);
    }

    private void setAttack(MouseEvent e) {

        if(gp.player.getWeapon().isRanged()) {
            if((e.getX() > adjustedX + TILE_SIZE || e.getY() > adjustedY + TILE_SIZE) ||
                    (e.getX() < adjustedX - TILE_SIZE || e.getY() < adjustedY - TILE_SIZE)) {

                //gp.playSE(this.getWeapon().getSoundNum());

                if (e.getY() > this.screenY) ammo.setProjectile(this.worldX, this.worldY, true, this);
                else if (e.getY() < this.screenY) {
                    // x offset
                    ammo.setProjectile(this.worldX + 20, this.worldY, true, this);
                }

                double mouseWX = e.getX() + this.worldX - this.adjustedX;
                double mouseWY = e.getY() + this.worldY - this.adjustedY;

                ammo.setDx(mouseWX - ammo.worldX);
                ammo.setDy(mouseWY - ammo.worldY);
                ammo.setRotateAngle(findTheAngle(e, this.adjustedX, this.adjustedY - 10) + 15);

                gp.projectiles.add(gp.player.getAmmo());
            }
        }
    }

    private double findTheAngle(MouseEvent e, double x, double y) {
        double dx = e.getX() - x;
        double dy = e.getY() - y;
        return Math.atan2(dy, dx);
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
                else if (e.getY() > this.screenY) image = attackDown;
                else image = attackUp;
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

        gp.SFXAdjuster.drawShadow(g2, x, y, sfxAdjustScreenX, sfxAdjustScreenY);
        if(!attacking) g2.drawImage(image, x, y, null);
        if(attacking) drawAttack(g2, image, x, y);

        // DISPLAY COLLISION
        /*g2.setColor(Color.RED);
        g2.drawRect(x + getSolidArea().x, y + getSolidArea().y,
                getSolidArea().width, getSolidArea().height);*/
    }

    private void drawAttack(Graphics2D g2, BufferedImage image, int x, int y) {
        if (e.getY() > this.screenY) {
            g2.drawImage(image, x, y, null);
            g2.drawImage(weapon.getWeaponImage(), x, y - 10, null);
        } else {
            g2.drawImage(weapon.getWeaponImage(), x + 20, y - 15, null);
            g2.drawImage(image, x, y, null);
        }
        if (!ammo.isAlive()) attacking = false;
    }
}
