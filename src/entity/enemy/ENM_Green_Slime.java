package entity.enemy;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import main.UtilityTool;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class ENM_Green_Slime extends Entity {

    private BufferedImage jump;
    private int tickCounter, jumpCounter;
    private boolean jumping;

    public ENM_Green_Slime(GamePanel gp) {
        super(gp, TILE_SIZE, TILE_SIZE, "down", 1,
                480, 1440, 0, 1440,
                1, 4,
                new Rectangle(1, 4, 48, 48),
                "park/enemies/", "Green Slime");

        maxLife = 15;
        currentLife = maxLife;
        damage = 8;
        knockback = TILE_SIZE;
        notDefaultSpritesNum = true;
        enemy = true;
        SFXPresent = true;

        getImages();
        loadAnimations();
    }

    private void getImages() {
        down1 = setup("slime_stand_1");
        down2 = setup("slime_stand_2");
        jump = setup("slime_jump");
    }

    private void loadAnimations() {
        dieImg = new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/park/enemies/slime_die.gif"))).getImage();
        dieImg = dieImg.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);
    }

    @Override
    public void update() {
        if (currentLife <= 0) {
            dying = true;
            dieCounter ++;

            // 32 frames death
            if (dieCounter == 32) {
                gp.entToRemove.add(this);
            }
        }
        if (!dying) super.update();
    }

    @Override
    protected void moveEntity() {
        checkResetPosition();
        if (!collisionOn) {
            tickCounter ++;
            if (tickCounter == 60) {
                tickCounter = 0;
            }
            jumpCounter ++;
            // 8 frames jump
            if (jumpCounter > 60 && jumpCounter < 68) {
                int choice = random.nextInt(120);
                if (choice > 30 && choice < 60) {
                    jumping = true;
                    changeCoords(speed * (choice / 2));
                }
            }
            else if (jumpCounter == 68) {
                jumping = false;
                jumpCounter = 0;
            }
        } else jumping = false;
    }

    @Override
    protected void setAction() {
        if (onPath) {
            Player player = gp.player;
            int goalCol = (player.worldX + player.getSolidArea().x) / TILE_SIZE;
            int goalRow = (player.worldY + player.getSolidArea().y) / TILE_SIZE;
            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter ++;
            slimeAI(180);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!dying) {
            if (!jumping) {
                if (tickCounter > 30 && tickCounter < 60) image = down1;
                else image = down2;
            } else image = jump;

            super.draw(g2);
        }
        else {
            screenX = worldX - gp.player.worldX + gp.player.screenX;
            screenY = worldY - gp.player.worldY + gp.player.screenY;
            UtilityTool.adjustCamera(gp, this, worldX, worldY);
            g2.drawImage(dieImg, screenX, screenY, null);
        }
    }

    private void slimeAI(int framesNum) {
       super.basicAI(framesNum);
       if (damaged) {
           onPath = true;
           damaged = false;
       }
    }
}
