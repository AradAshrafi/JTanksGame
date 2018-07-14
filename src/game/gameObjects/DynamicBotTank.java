package game.gameObjects;


import game.FileOperation.Map;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;
import java.util.ArrayList;

public class DynamicBotTank extends Tank {
    private int botHealth = 6;
    private int counterLimit = Map.UNIT_PIXELS_NUMBER / 2;
    private int movementUnitsCounter;
    private int tankSpeed = 8;
    private DynamicBotTankGun botTankGun;
    private OperationsDone userOperations;
    private LocationsPlacement locationsPlacement;


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
    private int nextMovementDirection;

    public DynamicBotTank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName, bulletSpeed);
        health = botHealth;
        movementUnitsCounter = 0;
        nextMovementDirection = 5;
//        this.userOperations = userOperations;
//        this.locationsPlacement = locationsPlacement;
        this.direction = 5;
        this.botTankGun = new DynamicBotTankGun(locX + 100 / 4, locY + 100 / 4, "icons/BigEnemyGun.png");
    }

    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects, LocationsPlacement locationsPlacement) {
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);
        movementUnitsCounter++;
        if (movementUnitsCounter % counterLimit == 0) updateNextDirection();
        updateNextLocation();
        botTankGun.update(cameraNorthBorder, cameraWestBorder, getLocX(), getLocY(), 100, occupierObjects, locationsPlacement.getTankLocX() - cameraWestBorder, locationsPlacement.getTankLocY() - cameraNorthBorder);
    }

    public void updateNextDirection() {
        nextMovementDirection = (int) (nextMovementDirection + (Math.random() * 8 + 1)) % 8 + 1;
    }

    public void updateNextLocation() {
        switch (nextMovementDirection) {
            case 1:
                nextLocX -= tankSpeed;
                break;
            case 2:
                nextLocX -= tankSpeed;
                nextLocY -= tankSpeed;
                break;
            case 3:
                nextLocY -= tankSpeed;
                break;
            case 4:
                nextLocX += tankSpeed;
                nextLocY -= tankSpeed;
                break;
            case 5:
                nextLocX += tankSpeed;
                break;
            case 6:
                nextLocX += tankSpeed;
                nextLocY += tankSpeed;
                break;
            case 7:
                nextLocY += tankSpeed;
                break;
            case 8:
                nextLocX -= tankSpeed;
                nextLocY += tankSpeed;
        }
    }

    public void paint(Graphics2D g2d) {
        rotateImageAndPaint(direction, g2d);
        botTankGun.paint(g2d);

    }
}
