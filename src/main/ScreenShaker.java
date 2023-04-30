package main;

import java.awt.Point;

public class ScreenShaker {
    GamePanel gp;
    private final Point gpLocation;
    private int shakeCounter;

    public ScreenShaker(GamePanel gp) {
        this.gp = gp;
        gpLocation = gp.getLocation();
    }

    public void shake() {

        int iDisplaceXBy = 1;
        int iDisplaceYBy = -1;

        Point position1 = new Point(gpLocation.x + iDisplaceXBy, gpLocation.y + iDisplaceYBy);
        Point position2 = new Point(gpLocation.x - iDisplaceXBy, gpLocation.y - iDisplaceYBy);

        // Shake every 3rd and 7th frame
        if (shakeCounter % 3 == 0) gp.setLocation(position1);
        else if (shakeCounter % 7 == 0) gp.setLocation(position2);

        shakeCounter ++;
    }

    public void resetScreenPosition() {
        shakeCounter = 0;
        gp.setLocation(gpLocation);
    }
}
