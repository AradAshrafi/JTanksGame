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

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState implements LocationsPlacement, OperationsDone {

    private GameObject[][] map;
    //things like brick,prizes and ... -->
    private ArrayList<UpdatableObjects> itemsInMap;
    //<--


    private Tank myTank;
    private GameCameraAndMyTank cameraAndMyTank;

    public boolean gameOver;
    private int relativeMouseX;
    private int relativeMouseY;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private boolean mouseMoved;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public GameState(Map mapOperation) {
        map = mapOperation.getMap();
        itemsInMap = mapOperation.getItemsInMap();

        myTank = new Tank(120, 30 * 120 - 240, "icons/myTank.png", 20);
        myTank.setRelativeLocX(120);
        myTank.setRelativeLocY(240);
        itemsInMap.add(myTank);

        relativeMouseX = 360;
        relativeMouseY = 360;
        cameraAndMyTank = new GameCameraAndMyTank((LocationsPlacement) (this), (OperationsDone) (this));

        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePress = false;


        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

    }


    /**
     * The method which updates the game state.
     */
    public void update() {
        if (mousePress) {
            CannonBullet newCannonBullet = new CannonBullet(myTank.getLocX(), myTank.getLocY(), "icons/HeavyBullet.png", getCameraWestBorder() + relativeMouseX, getCameraNorthBorder() + relativeMouseY, 20);
//            System.out.println(myTank.getLocX() + "  " + myTank.getLocY() + "  " + (getCameraWestBorder() + relativeMouseX) + "  " + (getCameraNorthBorder() + relativeMouseY) + "  ");
            itemsInMap.add(newCannonBullet);
            mousePress = false;
        }
        cameraAndMyTank.update();
        // cameraAndMyTank.updateByMouse();
        updateGameObjects(cameraAndMyTank.getCameraNorthBorder(), cameraAndMyTank.getCameraWestBorder());
    }


    private void updateGameObjects(int cameraNorthBorder, int cameraWestBorder) {
        Iterator<UpdatableObjects> it = itemsInMap.iterator();
        while (it.hasNext()) {
            UpdatableObjects currentObject = it.next();
            /**
             * will be completed in future to remove bullet if its passing forbidden part of map
             */

//            if(currentObject instanceof Bullet){
//                if(currentObject.getLocX() || currentObject.getLocY() )
//            }
            currentObject.update(cameraNorthBorder, cameraWestBorder);
        }

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
            if (!mousePress) {
                relativeMouseX = e.getX();
                relativeMouseY = e.getY();
                mousePress = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
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


    public ArrayList<UpdatableObjects> getItemsInMap() {
        return itemsInMap;
    }

    public GameObject[][] getMap() {
        return map;
    }

    public int getCameraNorthBorder() {
        return cameraAndMyTank.getCameraNorthBorder();
    }

    public int getCameraWestBorder() {
        return cameraAndMyTank.getCameraWestBorder();
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

