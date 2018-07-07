package game.gameSchematic; /*** In The Name of Allah ***/


import game.gameObjects.Bullet;
import game.gameObjects.GameObject;
import game.gameObjects.Tank;
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

    private ArrayList<GameObject> gameObjects;
    private Tank myTank;
    private GameCamera camera;

    public boolean gameOver;
    private int mouseX;
    private int mouseY;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private boolean mouseMoved;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public GameState() {
        myTank = new Tank(240, 480, "icons/myTank.png", 20);
        camera = new GameCamera((LocationsPlacement) (this), (OperationsDone) (this));
        gameObjects = new ArrayList<>();

        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePress = false;
        mouseX = 360;
        mouseY = 360;

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

    }


    /**
     * The method which updates the game state.
     */
    public void update() {
        if (mousePress) {
            Bullet newBullet = new Bullet(myTank.getLocX(), myTank.getLocY(), "icons/HeavyBullet.png", mouseX, mouseY, 20);
            gameObjects.add(newBullet);
        }
        camera.updateByKeys();
        camera.updateByMouse();
        updateGameObjects();
    }


    private void updateGameObjects() {
        Iterator<GameObject> it = gameObjects.iterator();
        while (it.hasNext()) {
            GameObject currentObject = it.next();
            /**
             * will be completed in future to remove bullet if its passing forbidden part of map
             */

//            if(currentObject instanceof Bullet){
//                if(currentObject.getLocX() || currentObject.getLocY() )
//            }
            currentObject.update();
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
            mouseX = e.getX();
            mouseY = e.getY();
            mousePress = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }


    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public int getMyTankLocX() {
        return myTank.getLocX();
    }

    public int getMyTankLocY() {
        return myTank.getLocY();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getCameraNorthBorder() {
        return camera.getCameraNorthBorder();
    }

    public int getCameraWestBorder() {
        return camera.getCameraWestBorder();
    }

    @Override
    public int getMouseLocX() {
        return mouseX;
    }

    @Override
    public int getMouseLocY() {
        return mouseY;
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
}

