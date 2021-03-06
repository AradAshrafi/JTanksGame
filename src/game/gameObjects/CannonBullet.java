package game.gameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CannonBullet extends DynamicObject {
    private int aimPositionX;
    private int aimPositionY;
    private int speed;
    private float cos;
    private float sin;

    public CannonBullet(int locX, int locY, int aimPositionX, int aimPositionY, int speed) {
        super(locX, locY, "icons/HeavyBullet.png");
        this.aimPositionX = aimPositionX;
        this.aimPositionY = aimPositionY;
        this.speed = speed;
        float hypotenuse = (float) Math.sqrt(Math.pow(aimPositionX - this.getLocX(), 2) + Math.pow(aimPositionY - this.getLocY(), 2));
        this.cos = (aimPositionX - this.getLocX()) / hypotenuse;
        this.sin = (aimPositionY - this.getLocY()) / hypotenuse;

    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
        /**
         * updating relative coordination
         */
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);


        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);

        /**
         * updating real coordination
         */
        this.setLocX(Math.round(this.getLocX() + speed * cos));
        this.setLocY(Math.round(this.getLocY() + speed * sin));

    }

    @Override
    /**
     * first rotate bullet to correct angle
     * then paint it
     */
    public void paint(Graphics2D g2d) {
        if ((this.getRelativeLocX() >= 0 && this.getRelativeLocX() <= 960) && ((this.getRelativeLocY() >= 0 && this.getRelativeLocY() <= 720))) {
            //-----> rotating bullet image
            BufferedImage buffer = this.getObjectImage();
            AffineTransform tx = new AffineTransform();
            if ((sin > 0 && cos > 0) || sin < 0 && cos > 0) {
                tx.rotate(Math.asin(sin), buffer.getWidth() / 2, buffer.getHeight() / 2);
            }
            /** ( sin < 0 && cos < 0 || sin > 0 && cos < 0 )
             in case sin<0 && cos<0  : Math.PI - Math.asin(sin)) is equal to Math.PI + | Math.asin(sin) |
             */
            else {
                tx.rotate((Math.PI - Math.asin(sin)), buffer.getWidth() / 2, buffer.getHeight() / 2);
               // System.out.println(sin + "   " + cos);
            }

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            buffer = op.filter(buffer, null);
            // <---------rotating finished

            g2d.drawImage(buffer, this.getRelativeLocX(), this.getRelativeLocY(), null);
        }
    }

}
