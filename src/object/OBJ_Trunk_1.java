package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class OBJ_Trunk_1 extends SuperObject {

    public OBJ_Trunk_1(GamePanel gp) {
        super(gp);

        name = "Trunk_1";
        collision = true;

        scaleX = 1;
        scaleY = 1;

        width = TILE_SIZE * scaleX;
        height = TILE_SIZE * scaleY;

        solidArea = new Rectangle(0, 100, width, 35);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/park/objects/trunk_1.png")));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
