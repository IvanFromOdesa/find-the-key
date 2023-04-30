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

    public boolean lmbPressed;

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
                    lmbPressed = true;
                    gp.player.setE(e);
                    gp.player.setAttacking(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(gp.gameState == gp.PLAY_STATE) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                lmbPressed = false;
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
            for(Entity entity : gp.entities) {
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
}
