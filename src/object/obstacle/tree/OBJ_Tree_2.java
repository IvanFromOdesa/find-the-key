package object.obstacle.tree;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Tree_2 extends SuperObject {

    public OBJ_Tree_2(GamePanel gp) {

        super(gp, true, 3, 3,
                144, 144, 7, 20,
                new Rectangle(20, 20, 127, 119),
                "/park/objects/trees/tree_2.png");
        name = "Tree_3";
    }
}
