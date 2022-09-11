package object.weapon.projectile;

import main.GamePanel;
import object.weapon.Weapon;

public class PRJ_Pink_Fireball extends Projectile {

    public PRJ_Pink_Fireball(GamePanel gp, Weapon gun) {
        super(gp, 32, 32,
                "/inventory/pink_fireball.png", gun);

        name = "Pink_Fireball";
        damage = 2;
        speed = 5;
        maxLife = 120;
    }
}
