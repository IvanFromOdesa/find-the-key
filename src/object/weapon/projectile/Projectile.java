package object.weapon.projectile;

import entity.Entity;
import entity.Player;
import lombok.Getter;
import lombok.Setter;
import main.GamePanel;
import object.SuperObject;
import object.weapon.Weapon;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import static main.GamePanel.SCREEN_HEIGHT;
import static main.GamePanel.SCREEN_WIDTH;

public abstract class Projectile extends SuperObject {

    @Getter
    private final BufferedImage projectileImage;

    public double worldX, worldY;
    public double defaultWorldX, defaultWorldY;
    @Getter
    private final Weapon gun;

    @Getter
    protected int damage;
    protected int speed;

    @Getter
    @Setter
    private boolean alive;

    @Setter
    private double dx, dy;
    Entity user;

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

        defaultWorldX = worldX;
        defaultWorldY = worldY;

        this.alive = alive;
        this.user = user;
    }

    public void update() {
        if (alive) tick();

        if (user instanceof Player) {
            if (worldX > SCREEN_WIDTH || worldY > SCREEN_HEIGHT || worldX <= 0 || worldY <= 0) {
                alive = false;
                worldX = defaultWorldX;
                worldY = defaultWorldY;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2.drawImage(projectileImage, (int) worldX, (int) worldY, null);

        // COLLISION
        /*g2.setColor(Color.RED);
        g2.drawRect((int) (worldX + solidArea.x), (int) (worldY + solidArea.y),
                solidArea.width, solidArea.height);*/
    }

    private void tick() {
        double moveX = (dx / 100) * speed;
        double moveY = (dy / 100) * speed;

        worldX += moveX;
        worldY += moveY;
    }
}
