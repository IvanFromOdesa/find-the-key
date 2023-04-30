package graphics;

import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class SFXAdjuster {
    GamePanel gp;
    // 50% transparent
    private final int alpha = 127;

    public SFXAdjuster(GamePanel gp) {
        this.gp = gp;
    }

    public void drawShadow(Graphics2D g2, int screenX, int screenY,
                           int sfxAdjustScreenX, int sfxAdjustScreenY) {
        g2.setColor(new Color(0, 0, 0, 127));
        g2.fillOval(screenX + sfxAdjustScreenX,screenY + sfxAdjustScreenY, 40, 20);
    }
}
