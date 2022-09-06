package object.obstacle.bush;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class OBJ_Bush_2 extends SuperObject {

    public OBJ_Bush_2(GamePanel gp) {
        super(gp, true, 1, 1,
                48, 48, 0, 0,
                new Rectangle(0, 0, 48, 48),
                "/park/objects/bush_2.png");

        name = "Bush_2";
    }
}
