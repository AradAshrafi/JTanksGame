package game.gameSchematic;


import game.FileOperation.Map;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.event.*;

public class UserOperation implements OperationsDone {
    public boolean gameOver;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    //new fields
    private GameCheatCode cheatCode;
    private int relativeMouseX;
    private int relativeMouseY;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, enterPressed, itemIsSelected;
    private boolean mousePressed;
    private boolean mouseMoved;


    public UserOperation() {
        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();
        relativeMouseX = 3 * Map.UNIT_PIXELS_NUMBER;
        relativeMouseY = 3 * Map.UNIT_PIXELS_NUMBER;
//        camera = new GameCamera((LocationsPlacement) (this), (OperationsDone) (this));
        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        enterPressed = false;
        //
        mousePressed = false;
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        //
        cheatCode = new
                GameCheatCode();
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
//                case KeyEvent.VK_ENTER:
//                    break;
                default:
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
                case KeyEvent.VK_ENTER:
                    enterPressed = true;
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            cheatCode.update(e);
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


    @Override
    public int getRelativeMouseX() {
        return relativeMouseX;
    }

    @Override
    public int getRelativeMouseY() {
        return relativeMouseY;
    }

    @Override
    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void setMousePressed(boolean trueOrFalse) {
        mousePressed = trueOrFalse;
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
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isEnterPressed() {
        boolean tmp = enterPressed;
        enterPressed = false;
        return tmp;
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
