package game.gameObjects;

import game.FileOperation.Map;

import java.awt.*;

public class Gun extends DynamicObject {

    public Gun(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    public void update(int cameraNorthBorder, int cameraWestBorder, int tankLocX, int tankLocY, int TankLength) {
        super.update(cameraNorthBorder, cameraWestBorder);
        setLocX(tankLocX + TankLength / 4);
        setLocY(tankLocY + TankLength / 4);
        setRelativeLocX(Math.max(getRelativeLocX(), 0));
        setRelativeLocX(Math.min(getRelativeLocX(), Map.MAP_WIDTH));
        setRelativeLocY(Math.max(getRelativeLocY(), 0));
        setRelativeLocY(Math.min(getRelativeLocY(), Map.MAP_HEIGHT));
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.getObjectImage(), this.getRelativeLocX(), this.getRelativeLocY(), null);


    }
}
