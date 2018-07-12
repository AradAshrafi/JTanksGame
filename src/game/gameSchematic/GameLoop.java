package game.gameSchematic;

import game.FileOperation.Map;
import game.SinglePlayerGame;

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

    public GameLoop(GameFrame frame) {
        canvas = frame;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        gameMap = new Map();
        gameMap.readMap();

        state = new GameState(gameMap);
        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    @Override
    public void run() {
        int[] userSelectedData = canvas.renderMenu(state);
        if (userSelectedData[0] == 0) {
            new SinglePlayerGame(canvas, state, FPS);
        }
    }
}
