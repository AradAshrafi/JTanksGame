package game.gameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CannonBullet extends Bullet {
//    private int aimPositionX;
//    private int aimPositionY;
//    private int speed;
//    private float cos;
//    private float sin;

    public CannonBullet(int locX, int locY, int aimPositionX, int aimPositionY, int speed) {
        super(locX, locY, "icons/HeavyBullet.png", aimPositionX, aimPositionY, speed);
    }

}
