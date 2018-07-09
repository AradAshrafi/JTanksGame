package game.gameObjects;

import game.FileOperation.Map;

import java.awt.*;

public class Tank extends GameObject {

    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;

    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public void paint(Graphics2D g2d) {
        if ((this.getRelativeLocX() >= 0 && this.getRelativeLocX() <= Map.MAP_WIDTH) &&
                ((this.getRelativeLocY() >= 0 && this.getRelativeLocY() <= Map.MAP_HEIGHT)))
            g2d.drawImage(this.getObjectImage(), this.getRelativeLocX(), this.getRelativeLocY(), null);
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        super.update(cameraNorthBorder, cameraWestBorder);
        setRelativeLocX(Math.max(getRelativeLocX(), 0));
        setRelativeLocX(Math.min(getRelativeLocX(), Map.MAP_WIDTH - SIDE_LENGTH));
        setRelativeLocY(Math.max(getRelativeLocY(), 0));
        setRelativeLocY(Math.min(getRelativeLocY(), Map.MAP_HEIGHT - SIDE_LENGTH));
    }
}
