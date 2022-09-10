package object.obstacle.tree;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Tree_4 extends SuperObject {

    public OBJ_Tree_4(GamePanel gp) {
        super(gp, true, 2, 4,
                96, 156, 23, 118,
                new Rectangle(0, 100, 50, 30),
                "/park/objects/trees/tree_4.png");

        name = "Tree_4";
    }
}
