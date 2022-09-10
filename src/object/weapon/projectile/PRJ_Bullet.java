package object.weapon.projectile;

import main.GamePanel;
import object.weapon.Weapon;

public class PRJ_Bullet extends Projectile {

    public PRJ_Bullet(GamePanel gp, Weapon gun) {
        super(gp, 9, 9,
                "/inventory/ammo.png", gun);

        name = "Bullet";
        damage = 2;
        speed = 5;
    }
}
