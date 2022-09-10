package object.obstacle.tree;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Tree_3 extends SuperObject {

    public OBJ_Tree_3(GamePanel gp) {
        super(gp, true, 1, 3,
                48, 132, 12, 100,
                new Rectangle(0, 100, 25, 27),
                "/park/objects/trees/tree_3.png");

        name = "Tree_3";
    }
}
