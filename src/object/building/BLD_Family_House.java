package object.building;

import main.GamePanel;
import object.SuperObject;

import java.awt.Rectangle;

public class BLD_Family_House extends SuperObject {

    public BLD_Family_House(GamePanel gp) {

        super(gp, true, 4, 6,
                174, 264, 10, 90,
                new Rectangle(20, 20, 154, 164),
                "/park/buildings/family_house.png");

        name = "Family_House";
    }
}
