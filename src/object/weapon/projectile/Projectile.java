package object.weapon.projectile;

import entity.Entity;
import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import main.UtilityTool;
import object.SuperObject;
import object.weapon.Weapon;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Projectile extends SuperObject {

    @Getter
    private final BufferedImage projectileImage;
    public double worldX, worldY;

    @Getter
    private final Weapon gun;

    @Getter
    protected int damage;
    protected int speed;

    @Getter
    @Setter
    private boolean alive;
    private int life;
    protected int maxLife;

    @Setter
    private double dx, dy;

    @Setter
    private double rotateAngle;

    @Getter
    private Entity user;

    protected Projectile(GamePanel gp, int width, int height,
                         String imagePath, Weapon gun) {
        super(gp, width, height, imagePath);
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
    }

    public void update() {
        if (alive) tick();

        life--;
        if(life <= 0) alive = false;
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
        screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

        UtilityTool.adjustCamera(gp, this, (int) worldX, (int) worldY);

        int cx = projectileImage.getWidth() / 2;
        int cy = projectileImage.getHeight() / 2;

        AffineTransform oldAT = g2.getTransform();

        g2.translate(cx + screenX, cy + screenY);
        g2.rotate(rotateAngle);
        g2.translate(-cx, -cy);
        g2.drawImage(projectileImage, 0, 0, null);
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
