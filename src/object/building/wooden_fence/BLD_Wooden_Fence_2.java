package object.building.wooden_fence;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class BLD_Wooden_Fence_2 extends SuperObject {

    public BLD_Wooden_Fence_2(GamePanel gp) {
        super(gp, true, 1, 3,
                48, 108, 10, 0,
                new Rectangle(10, 0, 35, 108),
                "/park/buildings/wooden-fence/wooden_fence_side_left_1.png");
    }
}
