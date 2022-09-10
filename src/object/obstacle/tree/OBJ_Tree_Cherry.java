package object.obstacle.tree;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class OBJ_Tree_Cherry extends SuperObject {

    public OBJ_Tree_Cherry(GamePanel gp) {
        super(gp, true, 2, 3,
                96, 144, 23, 118,
                new Rectangle(0, 100, 50, 30),
                "/park/objects/trees/cherry_tree.png");

        name = "Cherry_Tree";
    }
}
