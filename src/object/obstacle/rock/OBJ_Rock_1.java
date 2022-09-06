package object.obstacle.rock;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Rock_1 extends SuperObject {

    public OBJ_Rock_1(GamePanel gp) {
        super(gp, true, 1, 1,
                48, 48, 0, 0,
                new Rectangle(0, 0, 48, 48),
                "/park/objects/rock_1.png");

        name = "Rock_1";
    }
}
