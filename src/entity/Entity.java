package entity;

import java.awt.image.BufferedImage;

public class Entity {

    protected int x, y;
    protected int speed;

    protected BufferedImage stand, up1, up2, down1, down2, left1, left2, right1, right2;
    protected String direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;
}
