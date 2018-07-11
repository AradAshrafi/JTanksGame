package game.gameObjects;

import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class DynamicObject extends GameObject implements Runnable {
    private int currentDegree;
    private int health = 3;

    public DynamicObject(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
        currentDegree = 0;
    }

    public void movementControl(int nextLocX, int nextLocY) {

    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
    }


    /**
     * 1 -> North - South
     * 2 -> East -  West
     */
    void rotateImageAndPaint(boolean direction, Graphics2D g2d, OperationsDone userOperation) {
        //-> rotating start
        BufferedImage buffer = this.getObjectImage();
        AffineTransform tx = new AffineTransform();
        if (currentDegree > 5) {
            currentDegree = (2 * 5 - currentDegree) * -1;
        }
        if (currentDegree < -5)
            currentDegree = 2 * 5 + currentDegree; //is equal to ->  2 * Math.PI - |currentDegree|


        System.out.println(direction + "  " + currentDegree);
        /**
         * !direction means North or South
         * direction means West or East
         */
        if (userOperation.isKeyDownPressed() || userOperation.isKeyUpPressed() || userOperation.isKeyLeftPressed() || userOperation.isKeyRightPressed())
            if (!direction) {
                if (!(currentDegree == -2 || currentDegree == 2)) {
                    // Y >0
                    if (Math.abs(currentDegree - Math.PI / 2) < Math.abs(currentDegree + Math.PI / 2)) {
                        // X > 0
                        if (currentDegree < Math.PI / 2) {
//                        tx.rotate(Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree++;
                        }
                        // X < 0
                        else {
//                        tx.rotate(-Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree--;
                        }
                    }
                    // Y<0
                    else {
                        // X > 0
                        if (currentDegree > -Math.PI / 2) {
//                        tx.rotate(-Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree--;
                        }
                        // X < 0
                        else {
//                        tx.rotate(Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree++;
                        }
                    }
                }

            } else {
                if (!(currentDegree == 0 || currentDegree == 5 || currentDegree == -5)) {
                    System.out.println("here");
                    // X > 0
                    if (Math.abs(currentDegree) < Math.abs(currentDegree - Math.PI)) {
                        // Y < 0
                        if (currentDegree < 0) {
//                        tx.rotate(Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree++;
                        }
                        // Y > 0
                        else {
//                        tx.rotate(-Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree--;

                        }
                    }
                    // X < 0
                    else {
                        // Y > 0
                        if (currentDegree < Math.PI && currentDegree > 0) {
//                        tx.rotate(Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree++;

                        }
                        // Y < 0
                        else {
//                        tx.rotate(-Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
                            currentDegree--;

                        }
                    }
                }
            }
        if (currentDegree > 0)
            tx.rotate((currentDegree + 1) * Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
        else if (currentDegree < 0)
            tx.rotate((currentDegree - 1) * Math.PI / 3, buffer.getWidth() / 2, buffer.getHeight() / 2);
        else
            tx.rotate(0, buffer.getWidth() / 2, buffer.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        buffer = op.filter(buffer, null);
        // <---------rotating finished

        g2d.drawImage(buffer, this.getRelativeLocX(), this.getRelativeLocY(), null);
    }

    @Override
    public void run() {
        try {
            while (health > 0) {
                long start = System.currentTimeMillis();
                long delay = (15 - (System.currentTimeMillis() - start));
                if (delay > 0) {
                    Thread.sleep(delay);
                    System.out.println(getLocX() + "   asdfas" + getLocY());
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}


