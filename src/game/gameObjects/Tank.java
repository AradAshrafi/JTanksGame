package game.gameObjects;

import game.FileOperation.Map;

import java.awt.*;

public class Tank extends DynamicObject {

    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;

    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
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
