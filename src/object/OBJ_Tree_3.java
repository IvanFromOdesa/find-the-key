package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class OBJ_Tree_3 extends SuperObject {

    public OBJ_Tree_3(GamePanel gp) {
        super(gp);

        name = "Tree_3";
        collision = true;

        scaleX = 1;
        scaleY = 3;

        width = TILE_SIZE * scaleX;
        height = 132;

        solidAreaDefaultX = 12;
        solidAreaDefaultY = 100;

        solidArea = new Rectangle(0, 100, 25, 27);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/park/objects/tree_3.png")));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
