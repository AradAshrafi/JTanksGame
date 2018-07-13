package game.gameSchematic;

import game.FileOperation.Map;

import javax.swing.*;
import java.awt.*;

public class Game {
    private Server serverAndGameState;
    private Map gameMap;
    private UserOperation userOperation;

    public Game() {
        gameMap = new Map();
        gameMap.readMap();
        serverAndGameState = new Server();/*(gameMap.getMap(), gameMap.getOccupierObjects(), gameMap.getDynamicObjectsThreadPool(), 120, 30 * 120 - 240);*/
        userOperation = new UserOperation();

        // Initialize the global thread-pool
        ThreadPool baseThread = new ThreadPool();
        baseThread.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame("JTanks By Arad & Mohammad", userOperation);
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.addKeyListener(userOperation.getKeyListener());
                frame.addMouseListener(userOperation.getMouseListener());
                frame.addMouseMotionListener(userOperation.getMouseMotionListener());
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame, gameMap, userOperation);
                baseThread.execute(game);

                // and the game starts ...
            }
        });
    }
}
