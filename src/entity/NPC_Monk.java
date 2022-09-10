package entity;

import main.GamePanel;

import java.awt.Rectangle;

import static main.GamePanel.TILE_SIZE;

public class NPC_Monk extends Entity {

    private final String[] DIALOGUES = new String[4];

    public NPC_Monk(GamePanel gp) {
        super(gp, TILE_SIZE, TILE_SIZE, "stand", 1,
                480, 960, 480, 960,
                15, 36,
                new Rectangle(15, 36, 18, 12),
                "park/npc/monk/", "Monk");

        maxLife = 110;
        currentLife = maxLife;

        getNPC_MonkImage();
        setDialogue();
    }

    private void getNPC_MonkImage() {

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

    protected void setDialogue() {
        DIALOGUES[0] = "Watch where you going!";
        DIALOGUES[1] = "I have no time for you!";
        DIALOGUES[2] = "Whatever";
        DIALOGUES[3] = "Get out of my way, girl";
    }

    @Override
    protected void setAction() {

        actionLockCounter ++;

        // NPC persists movement direction for 3 seconds
        basicAI(180);
    }

    @Override
    public void speak() {
        setDialogueBehaviour(DIALOGUES);
    }
}
