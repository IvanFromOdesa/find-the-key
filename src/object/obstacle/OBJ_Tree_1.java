package object.obstacle;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class OBJ_Tree_1 extends SuperObject {

    public OBJ_Tree_1(GamePanel gp) {
        super(gp);

        name = "Tree_1";
        collision = true;

        scaleX = 1;
        scaleY = 3;

        width = TILE_SIZE * scaleX;
        height = TILE_SIZE * scaleY;

        solidAreaDefaultY = 100;

        solidArea = new Rectangle(0, 100, width, 35);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/park/objects/tree_1.png")));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
