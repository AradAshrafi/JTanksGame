package game.gameSchematic;

import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;


public class GameCamera {

    private int locXPrime, locYPrime;
    private int cameraNorthBorder, cameraSouthBorder, cameraWestBorder, cameraEastBorder;
    private int mouseXPrime, mouseYPrime;
    private int mouseX0;
    private int mouseY0;
    private int positionArea = 9; // a number in range 1-9
    private int positionMode = 9;//a number in range 1-9
    private LocationsPlacement necessaryLocations;
    private OperationsDone userOperations;

    public GameCamera(LocationsPlacement importantLocations, OperationsDone userOperations) {
        necessaryLocations = importantLocations;
        this.userOperations = userOperations;
        try {
            Robot r = new Robot();
            for (int i = 0; i < 10; i++) {
                r.mouseMove(288 + necessaryLocations.getMouseLocX(), 47 + necessaryLocations.getMouseLocY());
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }

        locXPrime = necessaryLocations.getTankLocX();
        locYPrime = Map.MAP_HEIGHT - 2 * Map.UNIT_PIXELS_NUMBER;
        mouseX0 = 0;
        mouseY0 = Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT;
        mouseXPrime = necessaryLocations.getMouseLocX() + mouseX0;
        mouseYPrime = necessaryLocations.getMouseLocY() + mouseY0;

        setPositionMode();
        setBorders();
        setPositionArea();

//        System.out.println("necessaryLocations.getMouseLocX() :" + necessaryLocations.getMouseLocX() + "necessaryLocations.getMouseLocY() + " + necessaryLocations.getMouseLocY());
//        System.out.println("necessaryLocations.getTankLocX() : " + necessaryLocations.getTankLocX() + "necessaryLocations.getTankLocY() :" + necessaryLocations.getTankLocY());
//        System.out.println("locXPrime" + locXPrime + ", locYPrime" + locYPrime);
//        System.out.println("mouseXPrime = " + mouseXPrime + "mouseYPrime" + mouseYPrime);
//        System.out.println("positionMode" + positionMode);
//        System.out.println("westB = " + cameraWestBorder + "northB = " + cameraNorthBorder);
//        System.out.println("pA : " + positionArea);
    }


    public void setPositionMode() {
        if (necessaryLocations.getTankLocX() < necessaryLocations.getMouseLocX()) {
            if (necessaryLocations.getTankLocY() < necessaryLocations.getMouseLocY()) positionMode = 1;
            else if (necessaryLocations.getTankLocY() > necessaryLocations.getMouseLocY()) positionMode = 2;
            else positionMode = 3;
        } else if (necessaryLocations.getTankLocX() > necessaryLocations.getMouseLocX()) {
            if (necessaryLocations.getTankLocY() < necessaryLocations.getMouseLocY()) positionMode = 4;
            else if (necessaryLocations.getTankLocY() > necessaryLocations.getMouseLocY()) positionMode = 5;
            else positionMode = 6;
        } else {
            if (necessaryLocations.getTankLocY() < necessaryLocations.getMouseLocY()) positionMode = 7;
            else if (necessaryLocations.getTankLocY() > necessaryLocations.getMouseLocY()) positionMode = 8;
            else positionMode = 9;
        }
    }

    public void setBorders() {

        int innerRectangleWidth = Math.abs(locXPrime - mouseXPrime);
        int innerRectangleHeight = Math.abs(locYPrime - mouseYPrime);
        int innerXSpace = (int) (0.5 * (GameFrame.GAME_WIDTH - innerRectangleWidth));
        int innerYSpace = (int) (0.5 * (GameFrame.GAME_HEIGHT - innerRectangleHeight));

        switch (positionMode) {
            case 1:
                //cameraSouthBorder = Math.max(GameFrame.GAME_HEIGHT, Math.min(mouseYPrime + innerYSpace, Map.HEIGHT*120));
                cameraSouthBorder = Math.min(GameFrame.GAME_HEIGHT, Math.max(Map.MAP_HEIGHT, mouseYPrime + innerYSpace));
                cameraNorthBorder = cameraSouthBorder - GameFrame.GAME_HEIGHT;
                //cameraEastBorder = Math.max(GameFrame.GAME_WIDTH, Math.min(mouseXPrime + innerXSpace, Map.WIDTH*120));
                cameraEastBorder = Math.min(GameFrame.GAME_WIDTH, Math.max(Map.MAP_WIDTH, mouseXPrime + innerXSpace));
                cameraWestBorder = cameraEastBorder - GameFrame.GAME_WIDTH;
                break;


            case 2:
                cameraNorthBorder = Math.min(Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT, Math.max(mouseYPrime - innerYSpace, 0));
                cameraSouthBorder = cameraNorthBorder + GameFrame.GAME_HEIGHT;
                cameraEastBorder = Math.min(GameFrame.GAME_WIDTH, Math.max(Map.MAP_WIDTH, mouseXPrime + innerXSpace));
                cameraWestBorder = cameraEastBorder - GameFrame.GAME_WIDTH;
                break;


            case 4:
                //cameraSouthBorder = Math.max(GameFrame.GAME_HEIGHT, Math.min(mouseYPrime + innerYSpace, Map.HEIGHT*120));
                cameraSouthBorder = Math.min(GameFrame.GAME_HEIGHT, Math.max(Map.MAP_HEIGHT, mouseYPrime + innerYSpace));
                cameraNorthBorder = cameraSouthBorder - GameFrame.HEIGHT;
                //cameraWestBorder = Math.max(Math.min(mouseXPrime - innerXSpace, (Map.WIDTH-1)*120), 0);
                cameraWestBorder = Math.min(Map.MAP_WIDTH - GameFrame.GAME_WIDTH, Math.max(mouseXPrime - innerXSpace, 0));
                cameraEastBorder = cameraWestBorder + GameFrame.GAME_WIDTH;
                break;

            case 5:
                //cameraNorthBorder = Math.min((Map.MAP_HEIGHT - 1)*120 ,Math.max(mouseYPrime - innerYSpace, 0));
                cameraNorthBorder = Math.min(Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT, Math.max(mouseYPrime - innerYSpace, 0));
                cameraSouthBorder = cameraNorthBorder + GameFrame.GAME_HEIGHT;
                //cameraWestBorder = Math.max(Math.min(mouseXPrime - innerXSpace, (Map.MAP_WIDTH-1)*120), 0);
                cameraWestBorder = Math.min(Map.MAP_WIDTH - GameFrame.GAME_WIDTH, Math.max(mouseXPrime - innerXSpace, 0));
                cameraEastBorder = cameraWestBorder + GameFrame.GAME_WIDTH;
                break;
        }
    }

    public void setPositionArea() {

        System.out.println("there WB is :" + cameraWestBorder);
        System.out.println("there NB is :" + cameraNorthBorder);
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

    public void updateByKeys() {

        if (userOperations.isKeyUpPressed()) {
            switch (positionArea) {
                case 1:
                    locYPrime -= 8;
                    mouseY0 -= 8;
                    break;

                case 2:
                    necessaryLocations.setTankLocY(-8);
                    locYPrime -= 8;
                    break;

                case 3:
                    locYPrime -= 8;
                    mouseY0 -= 8;
                    break;

                case 4:
                    necessaryLocations.setTankLocY(-8);
                    locYPrime -= 8;
                    break;

                case 5:
                    locYPrime -= 8;
                    mouseY0 -= 8;
                    break;

                case 6:
                    necessaryLocations.setTankLocY(-8);
                    locYPrime -= 8;
                    break;

                case 7:
                    necessaryLocations.setTankLocY(-8);
                    locYPrime -= 8;
                    break;

                case 8:
                    necessaryLocations.setTankLocY(-8);
                    locYPrime -= 8;
                    break;
                case 9:
                    necessaryLocations.setTankLocY(-8);
                    locYPrime -= 8;
                    break;

            }
        }
        if (userOperations.isKeyDownPressed()) {
            switch (positionArea) {
                case 1:
                    locYPrime += 8;
                    mouseY0 += 8;
                    break;
                case 2:
                    necessaryLocations.setTankLocY(+8);
                    locYPrime += 8;
                    break;
                case 3:
                    locYPrime += 8;
                    mouseY0 += 8;
                    break;
                case 4:
                    necessaryLocations.setTankLocY(+8);
                    locYPrime += 8;
                    break;
                case 5:
                    locYPrime += 8;
                    mouseY0 += 8;
                    break;
                case 6:
                    necessaryLocations.setTankLocY(+8);
                    locYPrime += 8;
                    break;
                case 7:
                    necessaryLocations.setTankLocY(+8);
                    locYPrime += 8;
                    break;
                case 8:
                    necessaryLocations.setTankLocY(+8);
                    locYPrime += 8;
                    break;
                case 9:
                    necessaryLocations.setTankLocY(+8);
                    locYPrime += 8;
                    break;
            }
        }
        if (userOperations.isKeyLeftPressed()) {
            switch (positionArea) {
                case 1:
                    locXPrime -= 8;
                    mouseX0 -= 8;
                    System.out.println("here1");
                    break;
                case 2:
                    locXPrime -= 8;
                    mouseX0 -= 8;
                    System.out.println("here2");
                    break;
                case 3:
                    necessaryLocations.setTankLocX(-8);
                    locXPrime -= 8;
                    System.out.println("here3");
                    break;
                case 4:
                    locXPrime -= 8;
                    mouseX0 -= 8;
                    System.out.println("here4");
                    break;
                case 5:
                    locXPrime -= 8;
                    mouseX0 -= 8;
                    System.out.println("here5");
                    System.out.println("cWB:" + cameraWestBorder);
                    System.out.println("cNB:" + cameraWestBorder);

                    break;
                case 6:
                    necessaryLocations.setTankLocX(-8);
                    locXPrime -= 8;
                    System.out.println("here6");
                    break;
                case 7:
                    necessaryLocations.setTankLocX(-8);
                    locXPrime -= 8;
                    System.out.println("here7");
                    break;
                case 8:
                    necessaryLocations.setTankLocX(-8);
                    locXPrime -= 8;
                    System.out.println("here8");
                    break;
                case 9:
                    necessaryLocations.setTankLocX(-8);
                    locXPrime -= 8;
                    System.out.println("here9");
                    break;
            }
        }
        if (userOperations.isKeyRightPressed()) {
            switch (positionArea) {
                case 1:
                    locXPrime += 8;
                    mouseX0 += 8;
                    break;
                case 2:
                    locXPrime += 8;
                    mouseX0 += 8;
                    break;
                case 3:
                    necessaryLocations.setTankLocX(+8);
                    locXPrime += 8;
                    break;
                case 4:
                    locXPrime += 8;
                    mouseX0 += 8;
                    break;
                case 5:
                    necessaryLocations.setTankLocX(+8);
                    locXPrime += 8;
                    break;
                case 6:
                    necessaryLocations.setTankLocX(+8);
                    locXPrime += 8;
                    break;
                case 7:
                    necessaryLocations.setTankLocX(+8);
                    locXPrime += 8;
                    break;
                case 8:
                    necessaryLocations.setTankLocX(+8);
                    locXPrime += 8;
                    break;
                case 9:
                    necessaryLocations.setTankLocX(+8);
                    locXPrime += 8;
                    break;
            }
        }

        mouseXPrime = necessaryLocations.getMouseLocX() + mouseX0;
        mouseYPrime = necessaryLocations.getMouseLocY() + mouseY0;

        /*
        locXPrime = Math.max(locXPrime, 0);
        locXPrime = Math.min(locXPrime, Map.MAP_WIDTH - diam);
        locYPrime = Math.max(locYPrime, 0);
        locYPrime = Math.min(locYPrime, Map.MAP_HEIGHT - diam);
        */
        setPositionMode();
        setBorders();
        setPositionArea();

    }

    public void updateByMouse() {
        if (userOperations.isMouseMoved()) {
            switch (positionArea) {
                case 1:
                    necessaryLocations.setTankLocX(GameFrame.GAME_WIDTH - necessaryLocations.getMouseLocX());
                    necessaryLocations.setTankLocY(GameFrame.GAME_HEIGHT - necessaryLocations.getMouseLocY());
                    break;
                case 2:
                case 4:
                    necessaryLocations.setTankLocX(GameFrame.GAME_WIDTH - necessaryLocations.getMouseLocX());
                    break;
                case 3:
                case 5:
                    necessaryLocations.setTankLocY(GameFrame.GAME_HEIGHT - necessaryLocations.getMouseLocY());
                    break;
            }
            mouseXPrime = necessaryLocations.getMouseLocX() + mouseX0;
            mouseYPrime = necessaryLocations.getMouseLocY() + mouseY0;
            System.out.println("GoodBye");
            setPositionMode();
            setBorders();
            setPositionArea();

            userOperations.setMouseMoved(false);
        }
    }

    public int getCameraNorthBorder() {
        return cameraNorthBorder;
    }

    public int getCameraWestBorder() {
        return cameraWestBorder;
    }
}



