package game.gameObjects;

import java.awt.*;

public class Bullet extends DynamicObject {
    private int aimPositionX;
    private int aimPositionY;
    private int speed;
    private float cos;
    private float sin;

    public Bullet(int locX, int locY, String pathName, int aimPositionX, int aimPositionY, int speed) {
        super(locX, locY, pathName);
        this.aimPositionX = aimPositionX;
        this.aimPositionY = aimPositionY;
        this.speed = speed;
        float hypotenuse = (float) Math.sqrt(Math.pow(aimPositionX - this.getLocX(), 2) + Math.pow(aimPositionY - this.getLocY(), 2));
        this.cos = (aimPositionX - this.getLocX()) / hypotenuse;
        this.sin = (aimPositionY - this.getLocY()) / hypotenuse;

    }

    @Override
    /**
     * update bullet location based on it's speed and it's angle
     */
    public void update() {
        this.setLocX(Math.round(this.getLocX() + speed * cos));
        this.setLocY(Math.round(this.getLocY() + speed * sin));
        System.out.println("im here");

    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.drawImage(this.getObjectImage(), this.getLocX(), this.getLocY(), null);
    }

}
