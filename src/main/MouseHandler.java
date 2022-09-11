package main;

import entity.Entity;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;

import static main.GamePanel.TILE_SIZE;

public class MouseHandler extends JFrame implements MouseListener, MouseMotionListener {

    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(gp.gameState == gp.PLAY_STATE) {
            if(Objects.equals(gp.player.getDirection(), "stand")) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    gp.player.setAttacking(true);

                    if(!gp.player.getAmmo().isAlive()) setPlayerAttack(e);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(gp.gameState == gp.PLAY_STATE) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                gp.player.setAttacking(false);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(gp.gameState == gp.PLAY_STATE) {
            for(Entity entity : gp.npc) {
                if(entity != null) {
                    if((e.getPoint().x > entity.screenX && e.getPoint().x < entity.screenX + TILE_SIZE) &&
                            (e.getPoint().y > entity.screenY && e.getPoint().y < entity.screenY + TILE_SIZE)) {
                        entity.setHighlight(true);
                        repaint();
                    }
                }
            }
        }
    }

    private double findTheAngle(MouseEvent e, double x, double y) {
        double dx = e.getX() - x;
        double dy = e.getY() - y;
        return Math.atan2(dy, dx);
    }

    private void setPlayerAttack(MouseEvent e) {

        if(gp.player.getGun().isRanged()) {
            if((e.getX() > gp.player.getAdjustedX() + TILE_SIZE
                    || e.getY() > gp.player.getAdjustedY() + TILE_SIZE) ||
                    (e.getX() < gp.player.getAdjustedX() - TILE_SIZE ||
                            e.getY() < gp.player.getAdjustedY() - TILE_SIZE)) {

                gp.player.getAmmo().setProjectile(gp.player.worldX,
                        gp.player.worldY, true, gp.player);

                double mouseWX = e.getX() + gp.player.worldX - gp.player.getAdjustedX();
                double mouseWY = e.getY() + gp.player.worldY - gp.player.getAdjustedY();

                gp.player.getAmmo().setDx(mouseWX - gp.player.getAmmo().worldX);
                gp.player.getAmmo().setDy(mouseWY - gp.player.getAmmo().worldY);

                gp.projectiles.add(gp.player.getAmmo());
                gp.player.getAmmo().setRotateAngle(findTheAngle(e, gp.player.getAdjustedX(),
                        gp.player.getAdjustedY() - 10) + 15);
            }
        }
    }
}