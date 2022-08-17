package entity;

import main.GamePanel;

import java.awt.Rectangle;
import java.util.Random;

public class NPC_Monk extends Entity{

    public NPC_Monk(GamePanel gp) {
        super(gp);

        imgSpec = "park/npc/monk/";

        direction = "stand";
        speed = 1;

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
        if(actionLockCounter == 180) {
            Random random = new Random();
            int choice = random.nextInt(140) + 1;

            if(choice <= 25) direction = "up";
            if(choice > 25 && choice <= 50) direction = "down";
            if(choice > 50 && choice <= 75) direction = "left";
            if(choice > 75 && choice <= 100) direction = "right";
            if(choice > 100 && choice <= 140) direction = "stand";
            actionLockCounter = 0;
        }
    }
}
