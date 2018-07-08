package game.gameSchematic;

import game.FileOperation.Map;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;


public class GameCameraAndMyTank {

    private int cameraNorthBorder, cameraSouthBorder, cameraWestBorder, cameraEastBorder;
    private int mouseX, mouseY;
    private int mouseX0;
    private int mouseY0;
    private int positionArea = 9; // a number in range 1-9
    private int positionMode = 9;//a number in range 1-9
    private LocationsPlacement necessaryLocations;
    private OperationsDone userOperations;
    private int innerRectangleWidth;
    private int innerRectangleHeight;
    private int innerXSpace;
    private int innerYSpace;
    private final int sideLength = 120;

    public GameCameraAndMyTank(LocationsPlacement importantLocations, OperationsDone userOperations) {
        necessaryLocations = importantLocations;
        this.userOperations = userOperations;
        try {
            Robot r = new Robot();
            for (int i = 0; i < 10; i++) {
                System.out.println("mouseX" + necessaryLocations.getRelativeMouseX() + "mouseY" + necessaryLocations.getRelativeMouseY());
                r.mouseMove(288 + necessaryLocations.getRelativeMouseX(), 47 + necessaryLocations.getRelativeMouseY());
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }

        mouseX0 = 0;
        mouseY0 = Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT;
        mouseX = necessaryLocations.getRelativeMouseX() + mouseX0;
        mouseY = necessaryLocations.getRelativeMouseY() + mouseY0;

        setPositionMode();
        setBorders();
        setPositionArea();

        printParameters();
    }


    public void setPositionMode() {
        if (necessaryLocations.getRelativeTankLocX() < necessaryLocations.getRelativeMouseX()) {
            if (necessaryLocations.getRelativeTankLocY() < necessaryLocations.getRelativeMouseY()) positionMode = 1;
            else if (necessaryLocations.getRelativeTankLocY() > necessaryLocations.getRelativeMouseY())
                positionMode = 2;
            else positionMode = 3;
        } else if (necessaryLocations.getRelativeTankLocX() > necessaryLocations.getRelativeMouseX()) {
            if (necessaryLocations.getRelativeTankLocY() < necessaryLocations.getRelativeMouseY()) positionMode = 4;
            else if (necessaryLocations.getRelativeTankLocY() > necessaryLocations.getRelativeMouseY())
                positionMode = 5;
            else positionMode = 6;
        } else {
            if (necessaryLocations.getRelativeTankLocY() < necessaryLocations.getRelativeMouseY()) positionMode = 7;
            else if (necessaryLocations.getRelativeTankLocY() > necessaryLocations.getRelativeMouseY())
                positionMode = 8;
            else positionMode = 9;
        }
    }

    public void setBorders() {

        innerRectangleWidth = Math.abs(necessaryLocations.getTankLocX() - mouseX);
        innerRectangleHeight = Math.abs(necessaryLocations.getTankLocY() - mouseY);
        innerXSpace = (int) (0.5 * (GameFrame.GAME_WIDTH - innerRectangleWidth));
        innerYSpace = (int) (0.5 * (GameFrame.GAME_HEIGHT - innerRectangleHeight));

        switch (positionMode) {
            case 1:
                cameraSouthBorder = Math.min(Map.MAP_HEIGHT, Math.max(GameFrame.GAME_HEIGHT, mouseY + innerYSpace));
                cameraNorthBorder = cameraSouthBorder - GameFrame.GAME_HEIGHT;
                cameraEastBorder = Math.min(Map.MAP_WIDTH, Math.max(GameFrame.GAME_WIDTH, mouseX + innerXSpace));
                cameraWestBorder = cameraEastBorder - GameFrame.GAME_WIDTH;
                break;


            case 2:
                cameraNorthBorder = Math.min(Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT, Math.max(mouseY - innerYSpace, 0));
                cameraSouthBorder = cameraNorthBorder + GameFrame.GAME_HEIGHT;
                cameraEastBorder = Math.min(Map.MAP_WIDTH, Math.max(GameFrame.GAME_WIDTH, mouseX + innerXSpace));
                cameraWestBorder = cameraEastBorder - GameFrame.GAME_WIDTH;
                break;


            case 4:
                cameraSouthBorder = Math.min(Map.MAP_HEIGHT, Math.max(GameFrame.GAME_HEIGHT, mouseY + innerYSpace));
                cameraNorthBorder = cameraSouthBorder - GameFrame.GAME_HEIGHT;
                cameraWestBorder = Math.min(Map.MAP_WIDTH - GameFrame.GAME_WIDTH, Math.max(mouseX - innerXSpace, 0));
                cameraEastBorder = cameraWestBorder + GameFrame.GAME_WIDTH;
                break;

            case 5:
                cameraNorthBorder = Math.min(Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT, Math.max(mouseY - innerYSpace, 0));
                cameraSouthBorder = cameraNorthBorder + GameFrame.GAME_HEIGHT;
                cameraWestBorder = Math.min(Map.MAP_WIDTH - GameFrame.GAME_WIDTH, Math.max(mouseX - innerXSpace, 0));
                cameraEastBorder = cameraWestBorder + GameFrame.GAME_WIDTH;
                break;
        }
    }

    public void setPositionArea() {

        if (cameraNorthBorder == 0) {
            if (cameraWestBorder == 0) positionArea = 6;
            else if (cameraEastBorder == Map.MAP_WIDTH) positionArea = 7;
            else positionArea = 2;
        } else if (cameraSouthBorder == Map.MAP_HEIGHT) {
            if (cameraWestBorder == 0) positionArea = 9;
            else if (cameraEastBorder == Map.MAP_WIDTH) positionArea = 8;
            else positionArea = 4;
        } else {
            if (cameraWestBorder == 0) positionArea = 5;
            else if (cameraEastBorder == Map.MAP_WIDTH) positionArea = 3;
            else positionArea = 1;
        }
    }

    public void update() {

        if (userOperations.isKeyUpPressed()) {
            printParameters();
            switch (positionArea) {
                case 1:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() - 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);

                    mouseY0 -= 8;
                    break;

                case 2:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() - 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    break;

                case 3:
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    mouseY0 -= 8;
                    break;

                case 4:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() - 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    break;

                case 5:
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    mouseY0 -= 8;
                    break;

                case 6:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() - 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    break;

                case 7:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() - 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    break;

                case 8:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() - 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    break;
                case 9:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() - 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
                    break;

            }
        }
        if (userOperations.isKeyDownPressed()) {
            printParameters();
            switch (positionArea) {
                case 1:
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    mouseY0 += 8;
                    break;
                case 2:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() + 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    break;
                case 3:
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    mouseY0 += 8;
                    break;
                case 4:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() + 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    break;
                case 5:
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    mouseY0 += 8;
                    break;
                case 6:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() + 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    break;
                case 7:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() + 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    break;
                case 8:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() + 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    break;
                case 9:
                    necessaryLocations.setRelativeTankLocY(necessaryLocations.getRelativeTankLocY() + 8);
                    necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
                    break;
            }
        }
        if (userOperations.isKeyLeftPressed()) {
            printParameters();
            switch (positionArea) {

                case 1:
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    mouseX0 -= 8;
                    System.out.println("here1");
                    break;
                case 2:
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    mouseX0 -= 8;
                    System.out.println("here2");
                    break;
                case 3:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() - 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    System.out.println("here3");
                    break;
                case 4:
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    mouseX0 -= 8;
                    System.out.println("here4");
                    break;
                case 5:
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    mouseX0 -= 8;
                    System.out.println("here5");
                    System.out.println("cWB:" + cameraWestBorder);
                    System.out.println("cNB:" + cameraWestBorder);

                    break;
                case 6:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() - 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    System.out.println("here6");
                    break;
                case 7:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() - 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    System.out.println("here7");
                    break;
                case 8:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() - 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    System.out.println("here8");
                    break;
                case 9:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() - 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
                    System.out.println("here9");
                    break;
            }
        }
        if (userOperations.isKeyRightPressed()) {
            printParameters();
            switch (positionArea) {
                case 1:
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    mouseX0 += 8;
                    break;
                case 2:
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    mouseX0 += 8;
                    break;
                case 3:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() + 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    break;
                case 4:
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    mouseX0 += 8;
                    break;
                case 5:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() + 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    break;
                case 6:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() + 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    break;
                case 7:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() + 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    break;
                case 8:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() + 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    break;
                case 9:
                    necessaryLocations.setRelativeTankLocX(necessaryLocations.getRelativeTankLocX() + 8);
                    necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
                    break;
            }
        }

        mouseX = necessaryLocations.getRelativeMouseX() + mouseX0;
        mouseY = necessaryLocations.getRelativeMouseY() + mouseY0;


        necessaryLocations.setRelativeTankLocX(Math.max(necessaryLocations.getRelativeTankLocX(), 0));
        necessaryLocations.setRelativeTankLocX(Math.min(necessaryLocations.getRelativeTankLocX(), Map.MAP_WIDTH - sideLength));
        necessaryLocations.setRelativeTankLocY(Math.max(necessaryLocations.getRelativeTankLocY(), 0));
        necessaryLocations.setRelativeTankLocY(Math.min(necessaryLocations.getRelativeTankLocY(), Map.MAP_HEIGHT - sideLength));

        setPositionMode();
        setBorders();
        setPositionArea();

        if (userOperations.getMouseMoved()) {
            printParameters();
            userOperations.setMouseMoved(false);
        }

    }


    public int getCameraNorthBorder() {
        return cameraNorthBorder;
    }

    public int getCameraWestBorder() {
        return cameraWestBorder;
    }

    public void printParameters() {
        System.out.println("innerXSpace" + innerXSpace + "innerYSpace" + innerYSpace);
        System.out.println("innerRectangleWidth" + innerRectangleWidth + innerRectangleHeight + "innerRectangleHeight");
        System.out.println("relativeMouseX :" + necessaryLocations.getRelativeMouseX() + "relativeMouseY + " + necessaryLocations.getRelativeMouseY());
        System.out.println("tankLocX : " + necessaryLocations.getTankLocX() + "tankLocY :" + necessaryLocations.getTankLocY());
        System.out.println("relativeLocX" + necessaryLocations.getRelativeTankLocX() + ", relativeLocY" + necessaryLocations.getRelativeTankLocY());
        System.out.println("mouseX = " + mouseX + "mouseY" + mouseY);
        System.out.println("positionMode" + positionMode);
        System.out.println("westB = " + cameraWestBorder + "northB = " + cameraNorthBorder);
        System.out.println("positionArea: " + positionArea);
    }
}



