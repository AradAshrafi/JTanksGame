package game.gameSchematic;

import game.FileOperation.Map;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;

/**
 * This class calculates and holds camera borders which is needed in the map loading and drawing.
 * @author Seyed Mohammad Hosseini Mojahed.
 */

public class GameCamera {

    private int cameraNorthBorder, cameraSouthBorder, cameraWestBorder, cameraEastBorder;
    private int mouseX, mouseY;
    private int positionMode = 9;//a number in range 1-9
    private int innerRectangleWidth;
    private int innerRectangleHeight;
    private int innerXSpace;
    private int innerYSpace;
    private int relativeLocXPrime;
    private int relativeLocYPrime;
    private LocationsPlacement necessaryLocations;
    private OperationsDone userOperations;

    public GameCamera(LocationsPlacement importantLocations, OperationsDone userOperations) {
        necessaryLocations = importantLocations;
        this.userOperations = userOperations;


        /*
         * the below try and catch block set the mouse location at the firs point with the coordinate relativeMouseX and relativeMouseY.
         */

        try {
            Robot r = new Robot();
            for (int i = 0; i < 10; i++) {
                r.mouseMove(288 + necessaryLocations.getRelativeMouseX(), 47 + necessaryLocations.getRelativeMouseY());
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }

        mouseX = necessaryLocations.getRelativeMouseX();
        mouseY = necessaryLocations.getRelativeMouseY() + Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT;

        setPositionMode();
        setBorders();
        print();

    }


    /**
     *This method set the position mode due to relative tank and mouse location.
     */

    public void setPositionMode() {
        if (necessaryLocations.getTankLocX() < mouseX) {
            if (necessaryLocations.getTankLocY() < mouseY) positionMode = 1;
            else if (necessaryLocations.getTankLocY() > mouseY) positionMode = 2;
            else positionMode = 3;
        } else if (necessaryLocations.getTankLocX() > mouseX) {
            if (necessaryLocations.getTankLocY() < mouseY) positionMode = 4;
            else if (necessaryLocations.getTankLocY() > mouseY) positionMode = 5;
            else positionMode = 6;
        } else {
            if (necessaryLocations.getTankLocY() < mouseY) positionMode = 7;
            else if (necessaryLocations.getTankLocY() > mouseY) positionMode = 8;
            else positionMode = 9;
        }
    }

    /**
     * to set north and south camera borders due to the position mode .
     */

    public void setHorizontalBorders(){
        switch (positionMode){
            case 1: case 3: case 4: case 6: case 7:
                cameraSouthBorder = Math.min(Map.MAP_HEIGHT, Math.max(GameFrame.GAME_HEIGHT, mouseY + innerYSpace));
                cameraNorthBorder = cameraSouthBorder - GameFrame.GAME_HEIGHT;
                break;

            case 2: case 5: case 8:
                cameraNorthBorder = Math.min(Map.MAP_HEIGHT - GameFrame.GAME_HEIGHT, Math.max(mouseY - innerYSpace, 0));
                cameraSouthBorder = cameraNorthBorder + GameFrame.GAME_HEIGHT;
                break;
        }
    }

    /**
     * to set west and east camera borders due to the position mode
     */

    public void setVerticalBorders(){
        switch (positionMode) {
            case 1:case 2: case 3: case 7: case 8:
                cameraEastBorder = Math.min(Map.MAP_WIDTH, Math.max(GameFrame.GAME_WIDTH, mouseX + innerXSpace));
                cameraWestBorder = cameraEastBorder - GameFrame.GAME_WIDTH;
                break;

            case 4:case 5: case 6:
                cameraWestBorder = Math.min(Map.MAP_WIDTH - GameFrame.GAME_WIDTH, Math.max(mouseX - innerXSpace, 0));
                cameraEastBorder = cameraWestBorder + GameFrame.GAME_WIDTH;
                break;
        }
    }

    /**
     *to set all camera borders by calling setHorizontalBorder() and setVerticalBorder().
     */

    public void setBorders() {

        innerRectangleWidth = Math.abs(necessaryLocations.getTankLocX() - mouseX);
        innerRectangleHeight = Math.abs(necessaryLocations.getTankLocY() - mouseY);
        innerXSpace = (int) (0.5 * (GameFrame.GAME_WIDTH - innerRectangleWidth));
        innerYSpace = (int) (0.5 * (GameFrame.GAME_HEIGHT - innerRectangleHeight));

        setHorizontalBorders();
        setVerticalBorders();

    }

    /**
     * updating relative and real location of mouse and tank due to the position area of camera
     * in four state key up, down, left or right pressed.
     */

    public void update() {

        if (userOperations.isKeyUpPressed()) {
            necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() - 8);
            innerUpdate();
            print();
        }

        if (userOperations.isKeyDownPressed()) {
            necessaryLocations.setTankLocY(necessaryLocations.getTankLocY() + 8);
            innerUpdate();
            print();
        }

        if (userOperations.isKeyLeftPressed()) {
            necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() - 8);
            innerUpdate();
            print();
        }

        if (userOperations.isKeyRightPressed()) {
            necessaryLocations.setTankLocX(necessaryLocations.getTankLocX() + 8);
            innerUpdate();
            print();
        }

        if (userOperations.getMouseMoved()){
            innerUpdate();
            print();
            userOperations.setMouseMoved(false);
        }
    }

    public int getCameraNorthBorder() {
        return cameraNorthBorder;
    }

    public int getCameraWestBorder() {
        return cameraWestBorder;
    }

    public void updateMouseLocation(){
        mouseX = necessaryLocations.getRelativeMouseX() + cameraWestBorder;
        mouseY = necessaryLocations.getRelativeMouseY() + cameraNorthBorder;
    }

    public void innerUpdate(){
        updateMouseLocation();
        setPositionMode();
        setBorders();
    }


    /**
     * this method prints the parameters need for debugging the camera updating.
     */

    public void  print(){
        System.out.println("innerRectangleWidth" + innerRectangleWidth + innerRectangleHeight + "innerRectangleHeight");
        System.out.println("innerXSpace" + innerXSpace + "innerYSpace" + innerYSpace );
        System.out.println("relativeMouseX :" + necessaryLocations.getRelativeMouseX() + "relativeMouseY + " + necessaryLocations.getRelativeMouseY());
        System.out.println("mouseX = " + mouseX + "mouseY" + mouseY);
        System.out.println("tankLocX : " + necessaryLocations.getTankLocX() + "tankLocY :" + necessaryLocations.getTankLocY());
        System.out.println("relativeLocX" + necessaryLocations.getRelativeTankLocX() + ", relativeLocY" + necessaryLocations.getRelativeTankLocY());
        System.out.println("positionMode" + positionMode);
        System.out.println("cameraWestB = " + cameraWestBorder + "cameraNorthB = " + cameraNorthBorder);
    }
}



