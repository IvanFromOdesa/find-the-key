package entity;

import main.GamePanel;

import java.awt.Rectangle;
import java.util.Random;

import static main.GamePanel.TILE_SIZE;

public class NPC_Monk extends Entity{

    public NPC_Monk(GamePanel gp) {
        super(gp);

        imgPath = "park/npc/monk/";

        direction = "stand";
        speed = 1;

        topBorder = TILE_SIZE * 10;
        bottomBorder = TILE_SIZE * 20;
        leftBorder = TILE_SIZE * 10;
        rightBorder = TILE_SIZE * 20;

        solidArea = new Rectangle(15, 36, 18, 12);
        solidAreaDefaultX = 15;
        solidAreaDefaultY = 36;

        getNPC_MonkImage();
    }

    public void getNPC_MonkImage() {

        stand = setup("monk_default");
        down1 = setup("monk_down_1");
        down2 = setup("monk_down_2");
        up1 = setup("monk_up_1");
        up2 = setup("monk_up_2");
        right1 = setup("monk_right_1");
        right2 = setup("monk_right_2");
        left1 = setup("monk_left_1");
        left2 = setup("monk_left_2");
    }

    @Override
    public void setAction() {

        actionLockCounter ++;

        // NPC persists movement direction for 3 seconds
        basicAI(180);
    }
}
