package object.weapon.gun;

import main.GamePanel;
import object.weapon.Weapon;

public class WPN_Gun_Virtue extends Weapon {

    public WPN_Gun_Virtue(GamePanel gp) {
        super(gp, 48, 48,
                "/inventory/gun_virtue.png");

        name = "Gun_Virtue";
        damage = 5;
        ranged = true;
    }
}
