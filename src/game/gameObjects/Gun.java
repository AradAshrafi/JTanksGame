package game.gameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gun extends RemovableObject {
    protected int aimLocX, aimLocY;
    protected int deltaX, deltaY;
    protected double degree = 0;

    public Gun(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    public void update(int cameraNorthBorder, int cameraWestBorder, int tankLocX, int tankLocY
            , int tankLength, ArrayList<GameObject> occupierObjects, int aimRelativeLocX, int aimRelativeLocY) {

        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
        setLocX(tankLocX);
        setLocY(tankLocY);
        updateDegree(aimRelativeLocX, aimRelativeLocY);
        System.out.println("update");
    }

    protected void updateDegree(int aimRelativeLocX, int aimRelativeLocY) {
        deltaY = aimRelativeLocY - getRelativeLocY() ;
        deltaX = aimRelativeLocX - getRelativeLocX() ;

        if (deltaY > 0 && deltaX > 0 || deltaY < 0 && deltaX > 0) {
            degree = Math.atan(deltaY / (deltaX * 1.0));
        } else if (deltaX != 0) {
            degree = Math.PI + Math.atan(deltaY / (deltaX * 1.0));
        } else {
            if (deltaY > 0)
                degree = Math.PI / 2;
            else
                degree = -Math.PI / 2;
        }
    }

    public void paint(Graphics2D g2d) {
        if ((this.getRelativeLocX() >= 0 && this.getRelativeLocX() <= 960) && ((this.getRelativeLocY() >= 0 && this.getRelativeLocY() <= 720))) {
            //-----> rotating Gun
            BufferedImage buffer = this.getObjectImage();
            AffineTransform tx = new AffineTransform();
            tx.rotate(degree, 50, 45);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            buffer = op.filter(buffer, null);
            // <---------rotating finished

            g2d.drawImage(buffer, this.getRelativeLocX(), this.getRelativeLocY(), null);
        }
    }

    @Override
    public void movementControl() {
    }

    @Override
    public void secondaryUpdate() {

    }
}
