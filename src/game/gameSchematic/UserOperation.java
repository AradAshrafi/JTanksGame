package game.gameSchematic;


import game.FileOperation.Map;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import javax.swing.*;
import java.awt.event.*;

public class UserOperation implements OperationsDone {
    public boolean gameOver;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    //new fields
    private GameCheatCode cheatCode;
    private int relativeMouseX;
    private int relativeMouseY;
    private boolean keyUP, keyDOWN, keyLEFT, keyRIGHT, keyUP2, keyDOWN2, keyLEFT2, keyRIGHT2, enterPressed, itemIsSelected;
    private boolean mousePressed, mouseRightPressed, mouseLeftPressed;
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
        //
        keyUP2 = false;
        keyDOWN2 = false;
        keyLEFT2 = false;
        keyRIGHT2 = false;
        //
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
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    keyUP2 = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    keyDOWN2 = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    keyLEFT2 = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    keyRIGHT2 = true;
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
            mousePressed = true;
            mouseRightPressed = SwingUtilities.isRightMouseButton(e);
            mouseLeftPressed = SwingUtilities.isLeftMouseButton(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePressed = false;
            mouseLeftPressed = false;
            mouseRightPressed = false;
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
    public int getMouseX() {
        return 0;
    }

    @Override
    public int getMouseY() {
        return 0;
    }

    @Override
    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public boolean isMouseRightPressed() {
        return mouseRightPressed;
    }

    @Override
    public void setMouseRightPressed(boolean trueOrFalse) {
        mouseRightPressed = trueOrFalse;

    }

    @Override
    public void setMouseLeftPressed(boolean trueOrFalse) {
        mouseLeftPressed = trueOrFalse;
    }

    @Override
    public boolean isMouseLeftPressed() {
        return mouseLeftPressed;
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
    public boolean isKeyUp2Pressed() {
        boolean tmp = keyUP2;
        keyUP2 = false;
        return tmp;
    }

    @Override
    public boolean isKeyDown2Pressed() {
        boolean tmp = keyDOWN2;
        keyDOWN2 = false;
        return tmp;
    }

//    @Override
//    public boolean isKeyRight2Pressed() {
//        boolean tmp = keyRIGHT2;
//        keyRIGHT2 = false;
//        return tmp;
//    }
//
//    @Override
//    public boolean isKeyLeft2Pressed() {
//        boolean tmp = keyLEFT2;
//        keyLEFT2 = false;
//        return tmp;
//    }

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
