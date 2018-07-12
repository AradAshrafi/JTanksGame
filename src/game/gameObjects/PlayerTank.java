package game.gameObjects;

import game.FileOperation.Map;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerTank extends DynamicObject {

    private int bulletSpeed;
    private int SIDE_LENGTH = 100;
    private int tankSpeed = 8;
    private OperationsDone userOperations;
    private BufferedImage cannonGun;
    private BufferedImage machineGun;
    private boolean currentGun;
    private Gun tankGun;
    private int health = 5;

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
    private int direction;

    /**
     * @param locX
     * @param locY
     * @param pathName
     * @param bulletSpeed
     */
    public PlayerTank(int locX, int locY, String pathName, int bulletSpeed, OperationsDone userOperations) {
        super(locX, locY, pathName);
        //this.resizeImage(100, 100);
        this.bulletSpeed = bulletSpeed;
        this.userOperations = userOperations;
        this.direction = 5;
        try {
            this.cannonGun = ImageIO.read(new File("icons/TankCannon.png"));
            this.machineGun = ImageIO.read(new File("icons/TankMachineGun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tankGun = new Gun(locX + SIDE_LENGTH / 4, locY + SIDE_LENGTH / 4, "icons/TankCannon.png");
        secondaryUpdate();
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);

        nextLocX = getLocX();
        nextLocY = getLocY();
        if (userOperations.isKeyUpPressed()) {
            nextLocY -= tankSpeed;
            direction = 3;
        }
        if (userOperations.isKeyDownPressed()) {
            nextLocY += tankSpeed;
            direction = 7;
        }
        if (userOperations.isKeyLeftPressed()) {
            nextLocX -= tankSpeed;
            direction = 1;
        }
        if (userOperations.isKeyRightPressed()) {
            nextLocX += tankSpeed;
            direction = 5;
        }
        if (userOperations.isKeyUpPressed() && userOperations.isKeyLeftPressed()) {
            direction = 2;
        }
        if (userOperations.isKeyUpPressed() && userOperations.isKeyRightPressed()) {
            direction = 4;
        }
        if (userOperations.isKeyDownPressed() && userOperations.isKeyRightPressed()) {
            direction = 6;
        }
        if (userOperations.isKeyDownPressed() && userOperations.isKeyLeftPressed()) {
            direction = 8;
        }

        tankGun.update(cameraNorthBorder, cameraWestBorder, getLocX(), getLocY(), SIDE_LENGTH, occupierObjects);
    }


    public void paint(Graphics2D g2d) {
        rotateImageAndPaint(direction, g2d, userOperations);
        this.tankGun.rotateImageAndPaint(direction, g2d, userOperations);

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
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
