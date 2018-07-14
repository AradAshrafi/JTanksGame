package game.gameObjects;

import game.gameSchematic.UserOperation;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerTank extends Tank {

    private int SIDE_LENGTH = 100;
    private int tankSpeed = 8;
    private static OperationsDone userOperations;
    private static LocationsPlacement locationsPlacement;
    private transient BufferedImage cannonGun;
    private transient BufferedImage machineGun;
    private boolean currentGun;
    private Gun tankGun;
    private int playerHealth = 5;

    /**
     * @param locX
     * @param locY
     * @param pathName
     * @param bulletSpeed
     */
    public PlayerTank(int locX, int locY, String pathName, int bulletSpeed, OperationsDone userOperations, LocationsPlacement locationsPlacement) {
        super(locX, locY, pathName, bulletSpeed);
        //this.resizeImage(100, 100);
        this.userOperations = userOperations;
        System.out.println("Player " + this.userOperations);

        this.locationsPlacement = locationsPlacement;
        this.direction = 5;
        health = playerHealth;
        try {
            this.cannonGun = ImageIO.read(new File("icons/TankCannon.png"));
            this.machineGun = ImageIO.read(new File("icons/TankMachineGun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tankGun = new Gun(locX + SIDE_LENGTH / 4, locY + SIDE_LENGTH / 4, "icons/TankCannon.png");
//        setRelativeLocX(400);
//        setRelativeLocY(400);

    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);
        System.out.println(userOperations);
//        System.out.println(getLocX() + "  " + getLocY() + "  " + "  " + cameraNorthBorder + "   " + cameraWestBorder + userOperations);
        if (userOperations.isKeyUpPressed()) {
            System.out.println("KEYUP");
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
            System.out.println("KEYRIGHT");

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
        secondaryUpdate(cameraNorthBorder, cameraWestBorder);
        locationsPlacement.setRelativeTankLocX(locationsPlacement.getTankLocX() - cameraWestBorder);
        locationsPlacement.setRelativeTankLocY(locationsPlacement.getTankLocY() - cameraNorthBorder);
        tankGun.update(cameraNorthBorder, cameraWestBorder, getLocX(), getLocY(), SIDE_LENGTH, occupierObjects, userOperations.getRelativeMouseX(), userOperations.getRelativeMouseY());
    }


    public void paint(Graphics2D g2d) {
//        System.out.println("painting taaaank " + getRelativeLocX() + " " + getRelativeLocY() + "  ======  " + getLocX() + "  " + getLocY());
        rotateImageAndPaint(direction, g2d, (OperationsDone) userOperations);
        tankGun.setObjectImage();
        tankGun.paint(g2d);

    }

//    public void setUserOperations(OperationsDone userOperations) {
//        this.userOperations = userOperations;
//        System.out.println(userOperations + "    " + this.userOperations);
//    }

}
