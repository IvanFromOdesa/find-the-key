package entity;

import main.GamePanel;

import java.awt.*;

import static main.GamePanel.TILE_SIZE;

public class NPC_Elvie extends Entity {

    private final String[] DIALOGUES = new String[4];

    public NPC_Elvie(GamePanel gp) {
        super(gp);

        imgPath = "park/npc/elvie/";
        width = TILE_SIZE;
        height = TILE_SIZE;

        direction = "stand";
        speed = 2;

        topBorder = TILE_SIZE * 10;
        bottomBorder = TILE_SIZE * 15;
        leftBorder = TILE_SIZE * 10;
        rightBorder = TILE_SIZE * 15;

        solidArea = new Rectangle(15, 36, 18, 12);
        solidAreaDefaultX = 15;
        solidAreaDefaultY = 36;

        getNPC_ElvieImage();
        setDialogue();
    }

    private void getNPC_ElvieImage() {

        stand = setup("elvie_default");
        down1 = setup("elvie_down_1");
        down2 = setup("elvie_down_2");
        up1 = setup("elvie_up_1");
        up2 = setup("elvie_up_2");
        right1 = setup("elvie_right_1");
        right2 = setup("elvie_right_2");
        left1 = setup("elvie_left_1");
        left2 = setup("elvie_left_2");
    }

    protected void setDialogue() {
        DIALOGUES[0] = "We have the aim!";
        DIALOGUES[1] = "Irlackye!";
        DIALOGUES[2] = "As long as I'm here...You are in safety.";
        DIALOGUES[3] = "Beware!";
    }

    @Override
    protected void setAction() {

        actionLockCounter ++;

        basicAI(150);
    }

    @Override
    public void speak() {
        setDialogueBehaviour(DIALOGUES);
    }
}
