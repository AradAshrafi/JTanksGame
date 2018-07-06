package game.gameObjects;

import java.awt.*;

public class Tank extends DynamicObject {

    private int bulletSpeed;

    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
    }


    @Override
    public void update() {

    }

    @Override
    public void paint(Graphics2D g2d) {

    }
}
