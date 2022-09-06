package object.building.wooden_fence;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class BLD_Wooden_Fence_6 extends SuperObject {

    public BLD_Wooden_Fence_6(GamePanel gp) {
        super(gp, true, 2, 1,
                96, 48, 0, 10,
                new Rectangle(0, 100, 120, 35),
                "/park/buildings/wooden-fence/wooden_fence_close.png");
    }
}
