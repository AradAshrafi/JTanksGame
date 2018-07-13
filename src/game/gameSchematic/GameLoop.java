package game.gameSchematic;

import game.FileOperation.Map;

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

    private ClientState state;
    private Map mapOperation;
    private UserOperation userOperation;

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    private final int FPS = 30;

    private GameFrame canvas;

//    private ThreadPool singlePlayerPool;

    public GameLoop(GameFrame frame, Map mapOperation, UserOperation userOperation) {
        canvas = frame;
        this.mapOperation = mapOperation;
        this.userOperation = userOperation;
//        singlePlayerPool = new ThreadPool();
//        singlePlayerPool.init();
    }

//    /**
//     * This must be called before the game loop starts.
//     */
//    public void init() {
//
//        canvas.addKeyListener(state.getKeyListener());
//        canvas.addMouseListener(state.getMouseListener());
//        canvas.addMouseMotionListener(state.getMouseMotionListener());
//    }

    @Override
    public void run() {
        int[] userSelectedData = canvas.renderMenu();
        if (userSelectedData[0] == 0) {
//            singlePlayerPool.execute(new SinglePlayerGame(canvas, state, FPS));
            Client client = new Client(userOperation, mapOperation, canvas, 120, Map.MAP_HEIGHT - 240);
            client.run();

        }
//        if (userSelectedData[0] == 1) {
//            GameFrame canvas2 = new GameFrame("JTanks By Arad & Mohammad");
//            canvas2.setLocationRelativeTo(null); // put frame at center of screen
//            canvas2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            canvas2.setVisible(true);
//            canvas2.initBufferStrategy();
//            new MultiPlayerGame(canvas, canvas2, state, FPS);
//        }
    }
}
