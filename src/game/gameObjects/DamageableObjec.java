package game.gameObjects;

import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class DamageableObjec extends GameObject implements Runnable {
    private int currentDegree;
    protected int cameraNorthBorder, cameraWestBorder;
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

    public DamageableObjec(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
//        setSideLength(100);
        occupierObjects = new ArrayList<>();
        nextLocX = locX;
        nextLocY = locY;
        currentDegree = 0;
    }

    public void movementControl() {
        boolean movementIsAllowed = true;
        int westBorder, eastBorder, northBorder, southBorder;

        int i;
        for (i = 0; i < occupierObjects.size(); i++) {
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
            setLocY(nextLocY);
            setLocX(nextLocX);
        } else checkDamageAndGetDamaged(i);
    }

    public void checkDamageAndGetDamaged(int occupierObjectNumber) {
        if (this instanceof CannonBullet) {
            ((CannonBullet) this).hit();
            if (occupierObjects.get(occupierObjectNumber) instanceof Tank) {
                for (int j = 0; j < 3; j++) {
                    ((Tank) occupierObjects.get(occupierObjectNumber)).getUnitDamaged();
                }
            }
            if (occupierObjects.get(occupierObjectNumber) instanceof Brick) {
                for (int j = 0; j < 3; j++) {
                    ((Brick) occupierObjects.get(occupierObjectNumber)).getUnitDamaged();
                }
            }
        } else if (this instanceof MachineGunBullet) {
            ((MachineGunBullet) this).hit();
            if (occupierObjects.get(occupierObjectNumber) instanceof Tank) {
                ((Tank) occupierObjects.get(occupierObjectNumber)).getUnitDamaged();

            }
            if (occupierObjects.get(occupierObjectNumber) instanceof Brick) {
                ((Brick) occupierObjects.get(occupierObjectNumber)).getUnitDamaged();

            }
        }
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {

        this.cameraNorthBorder = cameraNorthBorder;
        this.cameraWestBorder = cameraWestBorder;
        this.occupierObjects = occupierObjects;

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

    public void secondaryUpdate() {

        //  System.out.println("im here");
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
        //    System.out.println("rLY : " + getRelativeLocY() + "rLX : " + getRelativeLocX());
    }

    @Override
    public void run() {
        try {
            System.out.println(health);
            while (health > 0) {
                if (this instanceof Brick) System.out.println("jhnhjjjjjjjjjjjj"+health);
                long start = System.currentTimeMillis();
                long delay = (15 - (System.currentTimeMillis() - start));
                //System.out.println("delay is : " + delay);
                if (delay > 0) {
                    Thread.sleep(delay);
                    movementControl();
                    secondaryUpdate();

                }
            }
            System.out.println("to remove");
            occupierObjects.remove(this);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}


