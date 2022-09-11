package object.weapon.gun;

import main.GamePanel;
import object.weapon.Weapon;

public class WPN_Staff_Virtue extends Weapon {

    public WPN_Staff_Virtue(GamePanel gp) {
        super(gp, 32, 64, false,
                "/inventory/magic_staff.png");

        name = "Staff_Virtue";
        damage = 5;
        ranged = true;
        soundNum = 0;
    }
}
