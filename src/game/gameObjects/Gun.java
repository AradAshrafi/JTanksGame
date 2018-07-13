package game.gameObjects;

import game.FileOperation.Map;
import net.coobird.thumbnailator.Thumbnailator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gun extends DynamicObject {
    private int aimLocX;
    private int aimLocY;
    private int deltaX;
    private int deltaY;
    private double degree = 0;

    public Gun(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    public void update(int cameraNorthBorder, int cameraWestBorder, int tankLocX, int tankLocY
            , int tankLength, ArrayList<GameObject> occupierObjects, int aimRelativeLocX, int aimRelativeLocY) {
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);

        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
        setLocX(tankLocX + tankLength / 4);
        setLocY(tankLocY + tankLength / 4);

        updateDegree(aimRelativeLocX, aimRelativeLocY);
    }

    private void updateDegree(int mouseLocX, int mouseLocY) {
        deltaY = mouseLocY - getRelativeLocY();
        deltaX = mouseLocX - getRelativeLocX();

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
            System.out.println(deltaX + "  " + deltaY + "  " + degree);
            tx.rotate(degree, buffer.getWidth() * 1.0 / 4, buffer.getHeight() * 1.0 / 4);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            buffer = op.filter(buffer, null);
            // <---------rotating finished

            g2d.drawImage(buffer, this.getRelativeLocX(), this.getRelativeLocY(), null);
        }
    }
}
