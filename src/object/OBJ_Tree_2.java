package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class OBJ_Tree_2 extends SuperObject{

    public OBJ_Tree_2() {
        name = "Tree_2";

        scaleX = 3;
        scaleY = 3;

        width = TILE_SIZE * scaleX;
        height = TILE_SIZE * scaleY;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/tree_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
