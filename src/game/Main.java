package game;

import game.gameSchematic.GameFrame;
import game.gameSchematic.GameLoop;
import game.gameSchematic.ThreadPool;

import java.awt.*;
import javax.swing.JFrame;

/**
 * Program start.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class Main {

    public static void main(String[] args) {
        // Initialize the global thread-pool
//        ThreadPool.init();
        ThreadPool baseThread = new ThreadPool();
        baseThread.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame("JTanks By Arad & Mohammad");
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init();
                baseThread.execute(game);
                // and the game starts ...
            }
        });
    }
}

