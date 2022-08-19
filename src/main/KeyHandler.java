package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    // DEBUG
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // PLAY STATE
        if(gp.gameState == gp.PLAY_STATE) {
            if(code == KeyEvent.VK_W) upPressed = true;
            if(code == KeyEvent.VK_S) downPressed = true;
            if(code == KeyEvent.VK_A) leftPressed = true;
            if(code == KeyEvent.VK_D) rightPressed = true;
            if(code == KeyEvent.VK_ESCAPE) gp.gameState = gp.PAUSE_STATE;
            if(code == KeyEvent.VK_ENTER) enterPressed = true;

            // DEBUG
            if(code == KeyEvent.VK_M) checkDrawTime = !checkDrawTime;
        }

        // PAUSE STATE
        else if(gp.gameState == gp.PAUSE_STATE) {
            if(code == KeyEvent.VK_ESCAPE) gp.gameState = gp.PLAY_STATE;
        }

        // DIALOGUE STATE
        else if(gp.gameState == gp.DIALOGUE_STATE) {
            if(code == KeyEvent.VK_ENTER) gp.gameState = gp.PLAY_STATE;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) upPressed = false;
        if(code == KeyEvent.VK_S) downPressed = false;
        if(code == KeyEvent.VK_A) leftPressed = false;
        if(code == KeyEvent.VK_D) rightPressed = false;
    }
}
