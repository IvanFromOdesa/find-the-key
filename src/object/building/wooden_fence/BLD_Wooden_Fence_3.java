package object.building.wooden_fence;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class BLD_Wooden_Fence_3 extends SuperObject {

    public BLD_Wooden_Fence_3(GamePanel gp) {
        super(gp, true, 1, 2,
                48, 108, 10, 0,
                new Rectangle(10, 0, 35, 108),
                "/park/buildings/wooden-fence/wooden_fence_side_right_1.png");
    }
}
