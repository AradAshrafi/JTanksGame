package game.gameSchematic;

import game.FileOperation.Map;
import game.MultiPlayerGame;
import game.SinglePlayerGame;
import game.gameObjects.PlayerTank;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import javax.swing.*;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 * <p>
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 * http://gameprogrammingpatterns.com/game-loop.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    private final int FPS = 30;

    private GameFrame canvas;
    private GameState state;
    private Map gameMap;
    private ThreadPool singlePlayerPool;

    public GameLoop(GameFrame frame) {
        canvas = frame;
        singlePlayerPool = new ThreadPool();
        singlePlayerPool.init();
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        gameMap = new Map();
        gameMap.readMap();
        state = new GameState(gameMap.getMap(), gameMap.getOccupierObjects(), gameMap.getDynamicObjectsThreadPool(), 120, 30 * 120 - 240);

        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    @Override
    public void run() {
        int[] userSelectedData = canvas.renderMenu(state);
        if (userSelectedData[0] == 0) {
            singlePlayerPool.execute(new SinglePlayerGame(canvas, state, FPS));
        }
        if (userSelectedData[0] == 1) {
            GameFrame canvas2 = new GameFrame("JTanks By Arad & Mohammad");
            canvas2.setLocationRelativeTo(null); // put frame at center of screen
            canvas2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            canvas2.setVisible(true);
            canvas2.initBufferStrategy();
            new MultiPlayerGame(canvas, canvas2, state, FPS);
        }
    }
}
