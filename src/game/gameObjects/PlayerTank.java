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
     * 0 (false) -> North - South
     * 1 (true) -> East - West
     */
    private boolean direction;

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
        this.direction = true;
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

//        setRelativeLocX(Math.max(getRelativeLocX(), 0));
//        setRelativeLocX(Math.min(getRelativeLocX(), Map.MAP_WIDTH - SIDE_LENGTH));
//        setRelativeLocY(Math.max(getRelativeLocY(), 0));
//        setRelativeLocY(Math.min(getRelativeLocY(), Map.MAP_HEIGHT - SIDE_LENGTH));
//        System.out.println(this.getObjectImage().getWidth() + "  " + this.getObjectImage().getHeight());
        //secondaryUpdate();

        nextLocX = getLocX();
        nextLocY = getLocY();
        if (userOperations.isKeyUpPressed()) {
//            setLocY(getLocY() - tankSpeed);
            nextLocY -= tankSpeed;
            direction = false;
        }
        if (userOperations.isKeyDownPressed()) {
//            setLocY(getLocY() + tankSpeed);
            nextLocY += tankSpeed;
            direction = false;
        }
        if (userOperations.isKeyLeftPressed()) {
//            setLocX(getLocX() - tankSpeed);
            nextLocX -= tankSpeed;
            direction = true;
        }
        if (userOperations.isKeyRightPressed()) {
//            setLocX(getLocX() + tankSpeed);
            nextLocX += tankSpeed;
            direction = true;
        }

        tankGun.update(cameraNorthBorder, cameraWestBorder, getLocX(), getLocY(), SIDE_LENGTH, occupierObjects);
    }


    public void paint(Graphics2D g2d) {
//        if (getRelativeLocX() > (0-Map.UNIT_PIXELS_NUMBER) && relativeLocX < Map.MAP_WIDTH
//                && relativeLocY > (0-Map.UNIT_PIXELS_NUMBER) && relativeLocY < Map.MAP_HEIGHT)
//        g2d.drawImage(this.getObjectImage(), this.getRelativeLocX(), this.getRelativeLocY(), null);
        rotateImageAndPaint(direction, g2d, userOperations);
        this.tankGun.rotateImageAndPaint(direction, g2d, userOperations);

    }

    public void secondaryUpdate(){

      //  System.out.println("im here");
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
    //    System.out.println("rLY : " + getRelativeLocY() + "rLX : " + getRelativeLocX());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(120);
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
