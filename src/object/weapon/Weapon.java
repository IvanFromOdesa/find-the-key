package object.weapon;

import lombok.Getter;
import main.GamePanel;
import object.SuperObject;

import java.awt.image.BufferedImage;

public abstract class Weapon extends SuperObject {

    @Getter
    private final BufferedImage weaponImage;

    @Getter
    protected int damage;

    @Getter
    protected boolean ranged;

    @Getter
    protected int soundNum;

    protected Weapon(GamePanel gp, int width, int height, boolean collision, String imagePath) {
        super(gp, width, height, imagePath, false);

        this.collision = collision;
        weaponImage = (BufferedImage) image;
    }

    public void update() {

    }
}
