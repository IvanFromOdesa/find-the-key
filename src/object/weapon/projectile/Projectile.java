package object.weapon.projectile;

import entity.Entity;
import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.UtilityTool;
import object.SuperObject;
import object.weapon.Weapon;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;


public abstract class Projectile extends SuperObject {

    private final Image projectileImage;
    private Image crushImage;
    public double worldX, worldY;

    @Getter
    private final Weapon gun;

    @Getter
    protected int damage;
    protected int speed;
    private int dieCounter;
    @Getter
    private boolean alive;
    @Setter
    @Getter
    private int life;
    protected int maxLife;
    @Setter
    private double dx, dy;
    @Setter
    private double rotateAngle;

    @Getter
    private Entity user;

    protected Projectile(GamePanel gp, int width, int height, boolean animated,
                         String imagePath, Weapon gun) {
        super(gp, width, height, imagePath, animated);
        projectileImage = image;

        collision = true;
        solidArea = new Rectangle(0, 0, width, height);

        this.gun = gun;
    }

    public void setProjectile(double worldX, double worldY, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;

        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
        crushImage = loadImageAnimated("/inventory/pink_fireball_crush.gif");
    }

    public void update() {
        if (life <= 0) {
            dieCounter ++;
            if (dieCounter == 32) {
                alive = false;
                dieCounter = 0;
            }
        } else {
            if (alive) tick();
            gp.cChecker.checkProjectileObjects(this);
            life --;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
        screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

        UtilityTool.adjustCamera(gp, this, (int) worldX, (int) worldY);

        int cx = projectileImage.getWidth(null) / 2;
        int cy = projectileImage.getHeight(null) / 2;

        AffineTransform oldAT = g2.getTransform();

        g2.translate(cx + screenX, cy + screenY);
        g2.rotate(rotateAngle);
        g2.translate(-cx, -cy);
        if (life > 0) g2.drawImage(projectileImage, 0, 0, null);
        else if (dieCounter > 0) {
            g2.drawImage(crushImage,0,0, null);
        }
        g2.setTransform(oldAT);

        // COLLISION
        /*g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y,
                solidArea.width, solidArea.height);*/
    }

    private void tick() {
        double moveX = (dx / 100) * speed;
        double moveY = (dy / 100) * speed;

        worldX += moveX;
        worldY += moveY;
    }
}
