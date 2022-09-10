package object.obstacle.bush;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Bush_1 extends SuperObject {

    public OBJ_Bush_1(GamePanel gp) {
        super(gp, true, 1, 1,
                48, 48, 0, 0,
                new Rectangle(0, 0, 48, 48),
                "/park/objects/bushes/bush_1.png");

        name = "Bush_1";
    }
}
