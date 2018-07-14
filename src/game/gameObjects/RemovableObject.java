package game.gameObjects;

import game.gameSchematic.Game;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class RemovableObject extends GameObject implements Runnable {
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

    public RemovableObject(int locX, int locY, String pathName) {
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

        GameObject occupier = null;
        Iterator<GameObject> iterator = occupierObjects.iterator();
        while (iterator.hasNext()) {
            GameObject currentObject = iterator.next();
            westBorder = currentObject.getLocX() - getSideLength();
            eastBorder = currentObject.getLocX() + currentObject.getSideLength();
            northBorder = currentObject.getLocY() - getSideLength();
            southBorder = currentObject.getLocY() + currentObject.getSideLength();

            if (((nextLocX > westBorder && nextLocX < eastBorder) && (nextLocY > northBorder && nextLocY < southBorder)
                    && !(currentObject == this))) {
                movementIsAllowed = false;
                occupier = currentObject;
                break;
            }
        }
        if (movementIsAllowed) {
            setLocY(nextLocY);
            setLocX(nextLocX);
        } else checkRemovableObjects(occupier);
    }

    public void checkRemovableObjects(GameObject occupier) {
        if (this instanceof CannonBullet) {
            ((CannonBullet) this).hit();
            if (occupier instanceof Tank) {
                for (int j = 0; j < 3; j++) {
                    ((Tank) occupier).getUnitDamaged();
                }
            }
            if (occupier instanceof Brick) {
                for (int j = 0; j < 3; j++) {
                    ((Brick)occupier).getUnitDamaged();
                }
            }
        } else if (this instanceof MachineGunBullet) {
            ((MachineGunBullet) this).hit();
            if (occupier instanceof Tank) {
                ((Tank) occupier).getUnitDamaged();

            }
            if (occupier instanceof Brick) {
                ((Brick) occupier).getUnitDamaged();

            }
        }
        else if (this instanceof PlayerTank){
            if (occupier instanceof CannonFill) {
                ((CannonFill) occupier).getUnitDamaged();
                ((PlayerTank)this).cannonFill();
            }
            if (occupier instanceof MachineGunFill){
                ((MachineGunFill) occupier).getUnitDamaged();
                ((PlayerTank)this).machineGunFill();
            }
            if (occupier instanceof GunUpdate){
                ((PlayerTank)this).getUnitDamaged();
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


