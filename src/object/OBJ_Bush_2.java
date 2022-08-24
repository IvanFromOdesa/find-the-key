package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class OBJ_Bush_2 extends SuperObject{

    public OBJ_Bush_2(GamePanel gp) {
        super(gp);

        name = "Bush_2";
        collision = true;

        scaleX = 1;
        scaleY = 1;

        width = TILE_SIZE * scaleX;
        height = TILE_SIZE * scaleY;

        solidArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/park/objects/bush_2.png")));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
