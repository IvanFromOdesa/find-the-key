package entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;

    @Getter
    protected int speed;

    protected BufferedImage stand, up1, up2, down1, down2, left1, left2, right1, right2;

    @Getter
    protected String direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    @Getter
    protected Rectangle solidArea;

    @Getter
    @Setter
    protected boolean collisionOn = false;

    @Getter
    protected int solidAreaDefaultX, solidAreaDefaultY;
}
