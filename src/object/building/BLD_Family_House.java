package object.building;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class BLD_Family_House extends SuperObject {

    public BLD_Family_House(GamePanel gp) {

        super(gp);
        name = "Family_House";
        collision = true;

        scaleX = 4;
        scaleY = 6;

        width = 174;
        height = 264;

        solidAreaDefaultX = 10;
        solidAreaDefaultY = 90;

        solidArea = new Rectangle(20, 20, width - 20, height - 100);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/park/buildings/family_house.png")));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
