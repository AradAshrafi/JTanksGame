package game.gameSchematic;


public class SinglePlayerGame{
//    private GameFrame canvas;
//    private Server state;
//    private int FPS;
//    private GameCamera camera;
//    private boolean gameOver = false;
//    private KeyHandler keyHandler;
//    private MouseHandler mouseHandler;
//    //new fields
//    private GameCheatCode cheatCode;
//    private int relativeMouseX;
//    private int relativeMouseY;
//    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, enterPressed;
//    private boolean mousePressed;
//    private boolean mouseMoved;
//
//    public SinglePlayerGame(GameFrame canvas, Server state, int FPS) {
//        this.camera = new GameCamera((LocationsPlacement) (this), (OperationsDone) (this));
//        this.canvas = canvas;
//        this.state = state;
//        this.FPS = FPS;
//
//        relativeMouseX = 3 * Map.UNIT_PIXELS_NUMBER;
//        relativeMouseY = 3 * Map.UNIT_PIXELS_NUMBER;
////        camera = new GameCamera((LocationsPlacement) (this), (OperationsDone) (this));
//        gameOver = false;
//        //
//        keyUP = false;
//        keyDOWN = false;
//        keyRIGHT = false;
//        keyLEFT = false;
//        enterPressed = false;
//        //
//        mousePressed = false;
//        keyHandler = new KeyHandler();
//        mouseHandler = new MouseHandler();
//        //
//        cheatCode = new
//                GameCheatCode();
//    }
//
//    /**
//     * The method which updates the game state.
//     */
//    public void update() {
//
//        // cheatCode.update(kE);
//        if (mousePressed) {
//            CannonBullet newCannonBullet = new CannonBullet(playerTank.getLocX(), playerTank.getLocY(),
//                    camera.getCameraWestBorder() + relativeMouseX, camera.getCameraNorthBorder() + relativeMouseY, 20);
////            System.out.println(playerTank.getLocX() + "  " + playerTank.getLocY() + "  " + (getCameraWestBorder() + relativeMouseX) + "  " + (getCameraNorthBorder() + relativeMouseY) + "  ");
//            map.add(newCannonBullet);
//            mousePressed = false;
//        }
//        camera.update();
//        // cameraAndMyTank.updateByMouse();
//        updateGameObjects(camera.getCameraNorthBorder(), camera.getCameraWestBorder(), mapOccupierObjects);
//    }
//
//
//    private void updateGameObjects(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
//        Iterator<GameObject> it = map.iterator();
//        while (it.hasNext()) {
//            GameObject currentObject = it.next();
//            //TODO: will be completed in future to remove bullet if its passing forbidden part of map
////            if(currentObject instanceof Bullet){
////                if(currentObject.getLocX() || currentObject.getLocY() )
////            }
//            if (currentObject instanceof DynamicObject)
//                currentObject.update(cameraNorthBorder, cameraWestBorder, occupierObjects);
//            currentObject.update(cameraNorthBorder, cameraWestBorder);
//
//        }
//    }
//
//
//    public KeyListener getKeyListener() {
//        return keyHandler;
//    }
//
//    public MouseListener getMouseListener() {
//        return mouseHandler;
//    }
//
//    public MouseMotionListener getMouseMotionListener() {
//        return mouseHandler;
//    }
//
//    /**
//     * The keyboard handler.
//     */
//    class KeyHandler extends KeyAdapter {
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_UP:
//                    keyUP = true;
//                    break;
//                case KeyEvent.VK_DOWN:
//                    keyDOWN = true;
//                    break;
//                case KeyEvent.VK_LEFT:
//                    keyLEFT = true;
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    keyRIGHT = true;
//                    break;
//                case KeyEvent.VK_ESCAPE:
//                    gameOver = true;
//                    break;
//                case KeyEvent.VK_ENTER:
//                    enterPressed = true;
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_UP:
//                    keyUP = false;
//                    break;
//                case KeyEvent.VK_DOWN:
//                    keyDOWN = false;
//                    break;
//                case KeyEvent.VK_LEFT:
//                    keyLEFT = false;
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    keyRIGHT = false;
//                case KeyEvent.VK_ENTER:
//                    enterPressed = false;
//                    break;
//            }
//        }
//
//        @Override
//        public void keyTyped(KeyEvent e) {
//            cheatCode.update(e);
//        }
//    }
//
//    /**
//     * The mouse handler.
//     */
//    class MouseHandler extends MouseAdapter {
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//            if (!mousePressed) {
//                relativeMouseX = e.getX();
//                relativeMouseY = e.getY();
//                mousePressed = true;
//            }
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//            mousePressed = false;
//        }
//
//        @Override
//        public void mouseDragged(MouseEvent e) {
//            relativeMouseX = e.getX();
//            relativeMouseY = e.getY();
//        }
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//            relativeMouseX = e.getX();
//            relativeMouseY = e.getY();
//            setMouseMoved(true);
//        }
//    }
//
//
//    public ArrayList<GameObject> getMap() {
//        return map;
//    }
//
//    public PlayerTank getPlayerTank() {
//        return playerTank;
//    }
//
//    @Override
//    public int getRelativeMouseX() {
//        return relativeMouseX;
//    }
//
//    @Override
//    public int getRelativeMouseY() {
//        return relativeMouseY;
//    }
//
//    @Override
//    public int getTankLocX() {
//        return playerTank.getLocX();
//    }
//
//    @Override
//    public int getTankLocY() {
//        return playerTank.getLocY();
//    }
//
//    @Override
//    public void setTankLocX(int amount) {
//        playerTank.setLocX(amount);
//    }
//
//    @Override
//    public void setTankLocY(int amount) {
//        playerTank.setLocY(amount);
//    }
//
//    @Override
//    public int getRelativeTankLocX() {
//        return playerTank.getRelativeLocX();
//    }
//
//    @Override
//    public int getRelativeTankLocY() {
//        return playerTank.getRelativeLocY();
//    }
//
//    @Override
//    public void setRelativeTankLocX(int amount) {
//        playerTank.setRelativeLocX(amount);
//    }
//
//    @Override
//    public void setRelativeTankLocY(int amount) {
//        playerTank.setRelativeLocY(amount);
//    }
//
//    @Override
//    public boolean isKeyUpPressed() {
//        return keyUP;
//    }
//
//    @Override
//    public boolean isKeyDownPressed() {
//        return keyDOWN;
//    }
//
//    @Override
//    public boolean isKeyRightPressed() {
//        return keyRIGHT;
//    }
//
//    @Override
//    public boolean isKeyLeftPressed() {
//        return keyLEFT;
//    }
//
//    @Override
//    public boolean isMouseMoved() {
//        return mouseMoved;
//    }
//
//    @Override
//    public boolean isEnterPressed() {
//        return enterPressed;
//    }
//
//    @Override
//    public void setMouseMoved(boolean trueOrFalse) {
//        mouseMoved = trueOrFalse;
//    }
//
//    @Override
//    public boolean getMouseMoved() {
//        return mouseMoved;
//    }
//
//    @Override
//    public void run() {
//        boolean gameOver = false;
//        while (!gameOver) {
//            try {
//                long start = System.currentTimeMillis();
//                //
//                state.update();
//                canvas.render(state);
//                gameOver = state.gameOver;
//                //
//                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
//                if (delay > 0)
//                    Thread.sleep(delay);
//            } catch (InterruptedException ex) {
//            }
//        }
//    }
}
