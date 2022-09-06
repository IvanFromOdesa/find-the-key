package object.obstacle.tree;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Tree_1 extends SuperObject {

    public OBJ_Tree_1(GamePanel gp) {
        super(gp, true, 1, 3,
                48, 144, 0, 100,
                new Rectangle(0, 100, 48, 35),
                "/park/objects/tree_1.png");

        name = "Tree_1";
    }
}
