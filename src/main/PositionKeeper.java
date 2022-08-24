package main;

import java.awt.Graphics2D;

public abstract class PositionKeeper {
    protected int screenX, screenY;
    public int worldX, worldY;
    protected int width, height;

    public abstract void draw(Graphics2D g2);
}
