package game.gameObjects;

import java.awt.*;

public class Tank extends GameObject implements UpdatableObjects {

    private int bulletSpeed;

    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        /**
         * updating relative amounts
         */

        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
    }

    @Override
    public void paint(Graphics2D g2d) {
        if ((this.getRelativeLocX() >= 0 && this.getRelativeLocX() <= 960) && ((this.getRelativeLocY() >= 0 && this.getRelativeLocY() <= 720)))
            g2d.drawImage(this.getObjectImage(), this.getRelativeLocX(), this.getRelativeLocY(), null);
    }
}
