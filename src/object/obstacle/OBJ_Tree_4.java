package object.obstacle;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class OBJ_Tree_4 extends SuperObject {

    public OBJ_Tree_4(GamePanel gp) {
        super(gp);

        name = "Tree_4";
        collision = true;

        scaleX = 2;
        scaleY = 4;

        width = TILE_SIZE * scaleX;
        height = 156;

        solidAreaDefaultX = 23;
        solidAreaDefaultY = 118;

        solidArea = new Rectangle(0, 100, 50, 30);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/park/objects/tree_4.png")));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
