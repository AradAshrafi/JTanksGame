package game.gameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

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
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        /**
         * updating relative coordination
         */
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);

        /**
         * updating real coordination
         */
        this.setLocX(Math.round(this.getLocX() + speed * cos));
        this.setLocY(Math.round(this.getLocY() + speed * sin));
        System.out.println("im here");
    }

    @Override

    /**
     * first rotate bullet to correct angle
     * then paint it
     */
    public void paint(Graphics2D g2d) {
        if ((this.getRelativeLocX() >= 0 && this.getRelativeLocX() <= 960) && ((this.getRelativeLocY() >= 0 && this.getRelativeLocX() <= 720))) {
            //-----> rotating bullet image
            BufferedImage buffer = this.getObjectImage();
            AffineTransform tx = new AffineTransform();
            tx.rotate(Math.asin(sin), buffer.getWidth() / 2, buffer.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            buffer = op.filter(buffer, null);
            // <---------rotating finished

            g2d.drawImage(buffer, this.getLocX(), this.getLocY(), null);
        }
    }

}
