package entity;

import main.GamePanel;

import java.awt.Rectangle;

import static main.GamePanel.TILE_SIZE;

public class NPC_Viki extends Entity {

    private final String[] DIALOGUES = new String[4];

    public NPC_Viki(GamePanel gp) {
        super(gp);

        imgPath = "park/npc/viki/";
        width = TILE_SIZE;
        height = TILE_SIZE;

        direction = "stand";
        speed = 3;

        topBorder = TILE_SIZE * 10;
        bottomBorder = TILE_SIZE * 14;
        leftBorder = TILE_SIZE * 10;
        rightBorder = TILE_SIZE * 14;

        solidArea = new Rectangle(15, 36, 18, 12);
        solidAreaDefaultX = 15;
        solidAreaDefaultY = 36;

        getNPC_NatalieImage();
        setDialogue();
    }

    public void getNPC_NatalieImage() {
        stand = setup("viki_default");
        down1 = setup("viki_down_1");
        down2 = setup("viki_down_2");
        up1 = setup("viki_up_1");
        up2 = setup("viki_up_2");
        right1 = setup("viki_right_1");
        right2 = setup("viki_right_2");
        left1 = setup("viki_left_1");
        left2 = setup("viki_left_2");
    }

    protected void setDialogue() {
        DIALOGUES[0] = "Hi!";
        DIALOGUES[1] = "Haha!";
        DIALOGUES[2] = "I want...a bear";
        DIALOGUES[3] = "Hehe, catch meee!";
    }

    @Override
    public void setAction() {

        actionLockCounter++;

        basicAI(90);
    }

    @Override
    public void speak() {
        setDialogueBehaviour(DIALOGUES);
    }
}
