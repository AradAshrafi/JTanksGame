package game.sample.ball; /*** In The Name of Allah ***/


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

    public int locX, locY, diam;
    public int locXPrime, locYPrime;
    public int windowNorthBorder, windowSouthBorder, windowWestBorder, windowEastBorder;
    public boolean gameOver;
    public int mouseX, mouseY;
    public int mouseXPrime, mouseYPrime;
    public int mouseX0;
    public int mouseY0;

    public int positionArea = 9; // a number in range 1-9
    public int positionMode = 9;//a number in range 1-9

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public GameState() {
        locX = 120;
        locY = 480;
        locXPrime = locX;
        locYPrime = locY + (Map.HEIGHT-1)*120;

        diam = 120;
        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePress = false;
        mouseX = 240;
        mouseY = 360;
        mouseX0 = 0;
        mouseY0 = 0;
        mouseXPrime = mouseX + mouseX0;
        mouseYPrime = mouseY + mouseY0;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

        setPositionMode();
        setBorders();
        setPositionArea();
        System.out.println("pA : " + positionArea);

    }

    public void setPositionMode(){
        if (locX < mouseX){
            if (locY < mouseY) positionMode = 1;
            else if (locY > mouseY) positionMode = 2;
            else positionMode = 3;
        }
        else if (locX > mouseX){
            if (locY < mouseY) positionMode = 4;
            else if (locY > mouseY) positionMode = 5;
            else positionMode = 6;
        }
        else {
            if (locY < mouseY) positionMode = 7;
            else if (locY > mouseY) positionMode = 8;
            else positionMode = 9;
        }
    }

    public void setBorders(){

        int innerRectangleWidth = Math.abs(locX - mouseX);
        int innerRectangleHeight = Math.abs(locY - mouseY);
        int innerXSpace = (int)0.5*(GameFrame.GAME_WIDTH - innerRectangleWidth);
        int innerYSpace = (int)0.5*(GameFrame.GAME_HEIGHT - innerRectangleHeight);

        switch (positionMode){
            case 1 :
                windowSouthBorder = Math.max(GameFrame.GAME_HEIGHT, Math.min(mouseYPrime + innerYSpace, Map.HEIGHT*120));
                windowNorthBorder = windowSouthBorder - GameFrame.HEIGHT;
                windowEastBorder = Math.max(GameFrame.WIDTH, Math.min(mouseXPrime + innerXSpace, Map.WIDTH*120));
                windowWestBorder = windowEastBorder - GameFrame.WIDTH;
                break;


            case 2 :
                windowNorthBorder = Math.min((Map.HEIGHT - 1)*120 ,Math.max(mouseYPrime - innerYSpace, 0));
                windowSouthBorder = windowNorthBorder + GameFrame.GAME_HEIGHT;
                windowEastBorder = Math.max(GameFrame.WIDTH, Math.min(mouseXPrime + innerXSpace, Map.WIDTH*120));
                windowWestBorder = windowEastBorder - GameFrame.WIDTH;
                break;


            case 4 :
                windowSouthBorder = Math.max(GameFrame.GAME_HEIGHT, Math.min(mouseYPrime + innerYSpace, Map.HEIGHT*120));
                windowNorthBorder = windowSouthBorder - GameFrame.HEIGHT;
                windowWestBorder = Math.max(Math.min(mouseXPrime - innerXSpace, (Map.WIDTH-1)*120), 0);
                windowEastBorder = windowWestBorder + GameFrame.WIDTH;
                break;

            case 5 :
                windowNorthBorder = Math.min((Map.HEIGHT - 1)*120 ,Math.max(mouseYPrime - innerYSpace, 0));
                windowSouthBorder = windowNorthBorder + GameFrame.GAME_HEIGHT;
                windowWestBorder = Math.max(Math.min(mouseXPrime - innerXSpace, (Map.WIDTH-1)*120), 0);
                windowEastBorder = windowWestBorder + GameFrame.WIDTH;
                break;
        }
    }

    public void setPositionArea(){
        if (windowNorthBorder == 0){
            if (windowWestBorder == 0) positionArea = 6;
            else if (windowEastBorder == Map.WIDTH*120) positionArea = 7;
            else positionArea = 2;
        }
        else if (windowSouthBorder == Map.HEIGHT*120){
            if (windowWestBorder == 0) positionArea = 9;
            else if (windowEastBorder == Map.WIDTH*120) positionArea = 8;
            else positionArea = 4;
        }
        else {
            if (windowWestBorder == 0) positionArea = 5;
            else if (windowEastBorder == Map.WIDTH*120) positionArea = 3;
            else positionArea = 1;
        }
    }


    /**
     * The method which updates the game state.
     */
    public void update() {

        System.out.println("pA : " + positionArea);
        System.out.println("locX : " + locX + "locY : " + locY);

        if (mousePress) {
            locY = mouseY - diam / 2;
            locX = mouseX - diam / 2;
        }
        if (keyUP) {
            switch (positionArea){
                case 1 :
                    locYPrime -= 4;
                    mouseY0 -= 4;
                    break;

                case 2 :
                    locY -= 4;
                    locYPrime -= 4;
                    break;

                case 3:
                    locYPrime -= 4;
                    mouseY0 -= 4;
                    break;

                case 4:
                    locY -= 4;
                    locYPrime -= 4;
                    break;

                case 5:
                    locYPrime -= 4;
                    mouseY0 -= 4;
                    break;

                case 6:
                    locY -= 4;
                    locYPrime -= 4;
                    break;

                case 7:
                    locY -= 4;
                    locYPrime -= 4;
                    break;

                case 8:
                    locY -= 4;
                    locYPrime -= 4;
                    break;
                case 9:
                    locY -= 4;
                    locYPrime -= 4;
                    break;

            }
        }
        if (keyDOWN) {
            switch (positionArea){
                case 1 :
                    locYPrime += 4;
                    mouseY0 += 4;
                    break;
                case 2 :
                    locY += 4;
                    locYPrime += 4;
                    break;
                case 3:
                    locYPrime += 4;
                    mouseY0 += 4;
                    break;
                case 4:
                    locY += 4;
                    locYPrime += 4;
                    break;
                case 5:
                    locYPrime += 4;
                    mouseY0 += 4;
                    break;
                case 6:
                    locY += 4;
                    locYPrime += 4;
                    break;
                case 7:
                    locY += 4;
                    locYPrime += 4;
                    break;
                case 8:
                    locY += 4;
                    locYPrime += 4;
                    break;
                case 9:
                    locY += 4;
                    locYPrime += 4;
                    break;
            }
        }
        if (keyLEFT) {
            switch (positionArea){
                case 1 :
                    locXPrime -= 4;
                    mouseX0 -= 4;
                    break;
                case 2 :
                    locXPrime -= 4;
                    mouseX0 -= 4;
                    break;
                case 3:
                    locX -= 4;
                    locXPrime -= 4;
                    break;
                case 4:
                    locXPrime -= 4;
                    mouseX0 -= 4;
                    break;
                case 5:
                    locYPrime += 4;
                    mouseY0 += 4;
                    break;
                case 6:
                    locX -= 4;
                    locXPrime -= 4;
                    break;
                case 7:
                    locX -= 4;
                    locXPrime -= 4;
                    break;
                case 8:
                    locX -= 4;
                    locXPrime -= 4;
                    break;
                case 9:
                    locX -= 4;
                    locXPrime -= 4;
                    break;
            }
        }
        if (keyRIGHT) {
            switch (positionArea){
                case 1 :
                    locXPrime += 4;
                    mouseX0 += 4;
                    break;
                case 2 :
                    locXPrime += 4;
                    mouseX0 += 4;
                    break;
                case 3:
                    locX += 4;
                    locXPrime += 4;
                    break;
                case 4:
                    locXPrime += 4;
                    mouseX0 += 4;
                    break;
                case 5:
                    locX += 4;
                    locXPrime += 4;
                    break;
                case 6:
                    locX += 4;
                    locXPrime += 4;
                    break;
                case 7:
                    locX += 4;
                    locXPrime += 4;
                    break;
                case 8:
                    locX += 4;
                    locXPrime += 4;
                    break;
                case 9:
                    locX += 4;
                    locXPrime += 4;
                    break;
            }
        }

        mouseXPrime = mouseX + mouseX0;
        mouseYPrime = mouseY + mouseY0;

        locXPrime = Math.max(locXPrime, 0);
        locXPrime = Math.min(locXPrime, 8*GameFrame.GAME_WIDTH - diam);
        locYPrime = Math.max(locYPrime, 0);
        locYPrime = Math.min(locYPrime, 6*GameFrame.GAME_HEIGHT - diam);

        setPositionMode();
        setBorders();
        setPositionArea();

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
}

