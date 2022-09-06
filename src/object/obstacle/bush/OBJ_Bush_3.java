package object.obstacle.bush;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Bush_3 extends SuperObject {

    public OBJ_Bush_3(GamePanel gp) {
        super(gp, true, 1, 1,
                48, 48, 0, 0,
                new Rectangle(0, 0, 48, 48),
                "/park/objects/bush_3.png");

        name = "Bush_3";
    }
}
