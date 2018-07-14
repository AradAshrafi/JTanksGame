package game.gameObjects;

import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class DynamicObject extends GameObject {
    private int currentDegree;
    protected ArrayList<GameObject> occupierObjects;
    protected int nextLocX, nextLocY;
    protected int health;
    /**
     * 1 -> West
     * 2 -> North West
     * 3 -> North
     * 4 -> North East
     * 5 -> East
     * 6 -> South East
     * 7 -> South
     * 8 -> South West
     */
    protected int direction;

    public DynamicObject(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
        setSideLength(100);
        occupierObjects = new ArrayList<>();
        nextLocX = locX;
        nextLocY = locY;
        currentDegree = 0;

    }

    public void movementControl() {
        boolean movementIsAllowed = true;
        int westBorder, eastBorder, northBorder, southBorder;

        for (int i = 0; i < occupierObjects.size(); i++) {
            westBorder = occupierObjects.get(i).getLocX() - getSideLength();
            eastBorder = occupierObjects.get(i).getLocX() + occupierObjects.get(i).getSideLength();
            northBorder = occupierObjects.get(i).getLocY() - getSideLength();
            southBorder = occupierObjects.get(i).getLocY() + occupierObjects.get(i).getSideLength();

            if (((nextLocX > westBorder && nextLocX < eastBorder) && (nextLocY > northBorder && nextLocY < southBorder)
                    && !(occupierObjects.get(i) == this))) {
                movementIsAllowed = false;
                break;
            }
        }
        if (movementIsAllowed) {
            if (this instanceof PlayerTank) {
                setLocY(nextLocY);
                setLocX(nextLocX);
            }

        }
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
//        if (this instanceof PlayerTank)
//            System.out.println("sadfasdfasdfasdfasdflnasldkfjalksdfjg  " + cameraNorthBorder + "   " + cameraWestBorder);
//        this.cameraNorthBorder = cameraNorthBorder;
//        this.cameraWestBorder = cameraWestBorder;
        this.occupierObjects = occupierObjects;
        movementControl();
        secondaryUpdate(cameraNorthBorder, cameraWestBorder);

        nextLocX = getLocX();
        nextLocY = getLocY();

    }


    /**
     * 1 -> West
     * 2 -> North West
     * 3 -> North
     * 4 -> North East
     * 5 -> East
     * 6 -> South East
     * 7 -> South
     * 8 -> South West
     */
    void rotateImageAndPaint(int direction, Graphics2D g2d, OperationsDone userOperation) {
        //-> rotating start
        BufferedImage buffer = this.getObjectImage();
        AffineTransform tx = new AffineTransform();
        int coefficientOfRotation = 0;

        switch (direction) {
            case 1:
                coefficientOfRotation = 4;
                break;
            case 2:
                coefficientOfRotation = -3;
                break;
            case 3:
                coefficientOfRotation = -2;
                break;
            case 4:
                coefficientOfRotation = -1;
                break;
            case 5:
                coefficientOfRotation = 0;
                break;
            case 6:
                coefficientOfRotation = 1;
                break;
            case 7:
                coefficientOfRotation = 2;
                break;
            case 8:
                coefficientOfRotation = 3;
                break;
        }
        tx.rotate(coefficientOfRotation * Math.PI / 4, buffer.getWidth() / 2, buffer.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        buffer = op.filter(buffer, null);
        // <---------rotating finished

        g2d.drawImage(buffer, this.getRelativeLocX(), this.getRelativeLocY(), null);
    }

    public void secondaryUpdate(int cameraNorthBorder, int cameraWestBorder) {
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);

//        if (this instanceof PlayerTank) {
//            System.out.println(getLocY() + "    " + cameraNorthBorder);
//            System.out.println(getLocX() + "    " + cameraWestBorder);
//            System.out.println(this + "rLY : " + getRelativeLocY() + "rLX : " + getRelativeLocX());
//        }
    }

//    public int getCameraNorthBorder() {
//        return cameraNorthBorder;
//    }
//
//    public int getCameraWestBorder() {
//        return cameraWestBorder;
//    }

//    @Override
//    public void run() {
//        try {
//            while (health > 0) {
////                System.out.println("RUNNNNNNNNNN" + getCameraNorthBorder() + "   " + getCameraWestBorder());
//                long start = System.currentTimeMillis();
//                long delay = (15 - (System.currentTimeMillis() - start));
//                //System.out.println("delay is : " + delay);
//                if (delay > 0) {
//                    Thread.sleep(delay);
//                    //

//                    secondaryUpdate();
//                    if(this instanceof PlayerTank)
//                    System.out.println(getRelativeLocY() + "------------  " + getRelativeLocX());
//                }
//            }
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//    }
}


