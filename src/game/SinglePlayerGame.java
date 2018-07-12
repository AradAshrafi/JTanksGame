package game;

import game.gameSchematic.GameFrame;
import game.gameSchematic.GameState;

public class SinglePlayerGame {
    public SinglePlayerGame(GameFrame canvas, GameState state, int FPS) {
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
