package object.obstacle.tree;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Trunk_1 extends SuperObject {

    public OBJ_Trunk_1(GamePanel gp) {
        super(gp, true, 1, 1,
                48, 48, 0, 0,
                new Rectangle(0, 100, 48, 45),
                "/park/objects/trunk_1.png");

        name = "Trunk_1";
    }
}
