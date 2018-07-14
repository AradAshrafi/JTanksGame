package game.gameObjects;

import game.FileOperation.Map;
import game.gameSchematic.ThreadPool;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerTank extends Tank {

    private int SIDE_LENGTH = 100;
    private int tankSpeed = 8;
    private OperationsDone userOperations;
    private LocationsPlacement locationsPlacement;
    private BufferedImage cannonGun;
    private BufferedImage machineGun;
    private PlayerGun playerGun;
    private int playerHealth = 15;
    private int bulletSpeed = 10;
    private int canonShovel = 50;
    private int machineGunShovel = 300;
//    private boolean gunIsUpdated

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
        this.locationsPlacement = locationsPlacement;
        this.direction = 5;
        health = playerHealth;
        try {
            this.cannonGun = ImageIO.read(new File("icons/tankCannon1.png"));
            this.machineGun = ImageIO.read(new File("icons/tankMachineGun1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.playerGun = new PlayerGun(locX + SIDE_LENGTH / 4, locY + SIDE_LENGTH / 4, "icons/tankCannon1.png");
        gunType = 0;
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);

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

        playerGun.update(cameraNorthBorder, cameraWestBorder, getLocX(), getLocY(), SIDE_LENGTH, occupierObjects, userOperations.getRelativeMouseX(), userOperations.getRelativeMouseY());
    }

    public void shot(ArrayList<GameObject> map, ArrayList<GameObject> occupierObjects, ThreadPool dynamicObjectsThreadPool){
        if (gunType == 0){
            CannonBullet newCannonBullet = new CannonBullet(getLocX()+50, getLocY()+50,
                    cameraWestBorder + userOperations.getRelativeMouseX(), cameraNorthBorder + userOperations.getRelativeMouseY()
                    , bulletSpeed);
            occupierObjects.add(newCannonBullet);
            dynamicObjectsThreadPool.execute(newCannonBullet);
        }
        else {
            MachineGunBullet newMachineGunBullet = new MachineGunBullet(getLocX()+50, getLocY()+50,
                    cameraWestBorder + userOperations.getRelativeMouseX(), cameraNorthBorder + userOperations.getRelativeMouseY()
                    , bulletSpeed);
            occupierObjects.add(newMachineGunBullet);
            dynamicObjectsThreadPool.execute(newMachineGunBullet);
        }
    }

    @Override
    public void paint(Graphics2D g2d) {
        rotateImageAndPaint(direction, g2d);
        playerGun.paint(g2d);

    }

    public void changeGunType() {
        if (gunType == 0) playerGun.setObjectImage(machineGun);
        else if (gunType == 1)playerGun.setObjectImage(cannonGun);
        this.gunType = Math.abs(1-gunType);

    }

    public void cannonFill(){
        canonShovel = 50;
    }

    public void machineGunFill(){
        machineGunShovel = 300;
    }

    public void gunUpgrade(){
        if (gunType == 0) {
            try {
                this.cannonGun = ImageIO.read(new File("icons/tankCannon2.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerGun.setObjectImage(cannonGun);
            bulletSpeed = 20;
        }
        else {
            try {
                this.machineGun = ImageIO.read(new File("icons/tankMachineGun2.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerGun.setObjectImage(machineGun);
            bulletSpeed = 20;
        }

    }
}
