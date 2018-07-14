package game.gameSchematic;

import game.FileOperation.Map;

import java.net.Socket;


public class Client implements Runnable {
    private Socket clientSocket;
    private ClientState state;
    private GameFrame canvas;
    private UserOperation userOperation;

    public Client(UserOperation userOperation, Map map, GameFrame canvas, int playerTankLocX, int playerTankLocY) {
        this.userOperation = userOperation;
        state = new ClientState(userOperation, map.getUnderLayerObjects(), map.getOccupierObjects(), map.getDynamicObjectsThreadPool(), playerTankLocX, playerTankLocY);
        this.canvas = canvas;
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
                gameOver = userOperation.isGameOver();
                //
                long delay = (1000 / 30) - (System.currentTimeMillis() - start);
                if (delay > 0)
                    Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
    }
}
