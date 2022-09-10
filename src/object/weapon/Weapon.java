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

    protected Weapon(GamePanel gp, int width, int height, String imagePath) {
        super(gp, width, height, imagePath);

        collision = true;
        weaponImage = image;
    }

    public void update() {

    }
}
