package game.gameSchematic; /*** In The Name of Allah ***/


import game.FileOperation.Map;
import game.gameObjects.*;
import game.gameObjects.CannonBullet;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import static game.gameSchematic.GameCamera.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState implements LocationsPlacement, OperationsDone {

    private ArrayList<GameObject> map;
    private ArrayList<GameObject> mapOccupierObjects;

    //things like brick,prizes and ... -->

    private Tank myTank;
    private GameCamera camera;
    private boolean gunType;
    public boolean gameOver;
    private int relativeMouseX;
    private int relativeMouseY;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePressed;
    private boolean mouseMoved;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public GameState(Map mapOperation) {

        map = mapOperation.getMap();
        mapOccupierObjects = mapOperation.getOccupierObjects();
        myTank = new Tank(120, 30 * 120 - 240, "icons/MyTank.png", 20);
        myTank.setRelativeLocX(Map.UNIT_PIXELS_NUMBER);
        myTank.setRelativeLocY(2*Map.UNIT_PIXELS_NUMBER);
        map.add(myTank);
        relativeMouseX = 3*Map.UNIT_PIXELS_NUMBER;
        relativeMouseY = 3*Map.UNIT_PIXELS_NUMBER;
        camera = new GameCamera((LocationsPlacement) (this), (OperationsDone) (this));

        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePressed = false;
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

    }


    /**
     * The method which updates the game state.
     */
    public void update() {
        if (mousePressed) {
            CannonBullet newCannonBullet = new CannonBullet(myTank.getLocX(), myTank.getLocY(),
                    camera.getCameraWestBorder() + relativeMouseX, camera.getCameraNorthBorder() + relativeMouseY, 20);
//            System.out.println(myTank.getLocX() + "  " + myTank.getLocY() + "  " + (getCameraWestBorder() + relativeMouseX) + "  " + (getCameraNorthBorder() + relativeMouseY) + "  ");
            map.add(newCannonBullet);
            mousePressed = false;
        }
        camera.update();
        // cameraAndMyTank.updateByMouse();
        updateGameObjects(camera.getCameraNorthBorder(), camera.getCameraWestBorder(), mapOccupierObjects);
    }


    private void updateGameObjects(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
        Iterator<GameObject> it = map.iterator();
        while (it.hasNext()) {
            GameObject currentObject = it.next();
            /**
             * will be completed in future to remove bullet if its passing forbidden part of map
             */

//            if(currentObject instanceof Bullet){
//                if(currentObject.getLocX() || currentObject.getLocY() )
//            }
            currentObject.update(cameraNorthBorder, cameraWestBorder);
            //System.out.println(currentObject.getLocX() + "," + currentObject.getLocY()+ "");
        }
        myTank.update(cameraNorthBorder, cameraWestBorder);
    }

    public KeyListener getKeyListener() {
        return keyHandler;
    }

    public MouseListener getMouseListener() {
        return mouseHandler;
    }

    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gameOver = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;
            }
        }
    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (!mousePressed) {
                relativeMouseX = e.getX();
                relativeMouseY = e.getY();
                mousePressed = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePressed = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            relativeMouseX = e.getX();
            relativeMouseY = e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            relativeMouseX = e.getX();
            relativeMouseY = e.getY();
            setMouseMoved(true);
        }
    }



    public ArrayList<GameObject> getMap() {
        return map;
    }

    public Tank getMyTank() {
        return myTank;
    }


    @Override
    public int getRelativeMouseX() {
        return relativeMouseX;
    }

    @Override
    public int getRelativeMouseY() {
        return relativeMouseY;
    }

    @Override
    public int getTankLocX() {
        return myTank.getLocX();
    }

    @Override
    public int getTankLocY() {
        return myTank.getLocY();
    }

    @Override
    public void setTankLocX(int amount) {
        myTank.setLocX(amount);
    }

    @Override
    public void setTankLocY(int amount) {
        myTank.setLocY(amount);
    }

    @Override
    public int getRelativeTankLocX() {
        return myTank.getRelativeLocX();
    }

    @Override
    public int getRelativeTankLocY() {
        return myTank.getRelativeLocY();
    }

    @Override
    public void setRelativeTankLocX(int amount) {
        myTank.setRelativeLocX(amount);
    }

    @Override
    public void setRelativeTankLocY(int amount) {
        myTank.setRelativeLocY(amount);
    }


    @Override
    public boolean isKeyUpPressed() {
        return keyUP;
    }

    @Override
    public boolean isKeyDownPressed() {
        return keyDOWN;
    }

    @Override
    public boolean isKeyRightPressed() {
        return keyRIGHT;
    }

    @Override
    public boolean isKeyLeftPressed() {
        return keyLEFT;
    }

    @Override
    public boolean isMouseMoved() {
        return mouseMoved;
    }

    @Override
    public void setMouseMoved(boolean trueOrFalse) {
        mouseMoved = trueOrFalse;
    }

    @Override
    public boolean getMouseMoved() {
        return mouseMoved;
    }
}

