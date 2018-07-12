package game;

import game.gameSchematic.GameFrame;
import game.gameSchematic.GameState;

public class SinglePlayerGame implements Runnable {
    private GameFrame canvas;
    private GameState state;
    private int FPS;

    public SinglePlayerGame(GameFrame canvas, GameState state, int FPS) {
        this.canvas = canvas;
        this.state = state;
        this.FPS = FPS;
    }

    @Override
    public void run() {
        boolean gameOver = false;
        while (!gameOver) {
            try {
                long start = System.currentTimeMillis();
                //
                state.update();
                canvas.render(state);
                gameOver = state.gameOver;
                //
                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
    }
}
