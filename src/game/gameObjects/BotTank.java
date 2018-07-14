package game.gameObjects;


import game.FileOperation.Map;

import java.util.ArrayList;

public class BotTank extends Tank {
    private int botHealth = 3;
    private int counterLimit = Map.UNIT_PIXELS_NUMBER/2;
    private int movementUnitsCounter;
    private int tankSpeed = 8;

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
    public BotTank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName, bulletSpeed);
        health = botHealth;
        movementUnitsCounter = 0;
        nextMovementDirection = 5;
    }
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects){
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);
        movementUnitsCounter ++;
        if (movementUnitsCounter%counterLimit == 0) updateNextDirection();
        updateNextLocation();
    }

    public void updateNextDirection(){
        nextMovementDirection = (int)(nextMovementDirection + (Math.random()*8 +1))%8+1;
    }

    public void updateNextLocation(){
        switch (nextMovementDirection){
            case 1:
                nextLocX -= tankSpeed;
                break;
            case 2:
                nextLocX -=tankSpeed;
                nextLocY -=tankSpeed;
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
}
