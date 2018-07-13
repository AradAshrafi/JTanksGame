package game.gameSchematic;

import game.gameObjects.GameObject;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 8 * GAME_HEIGHT / 6;  // wide aspect ratio
    private long lastRender;
    private ArrayList<Float> fpsHistory;
    private BufferStrategy bufferStrategy;
    private OperationsDone userOperation;
    private Graphics2D g2d;
    boolean enough = false;

    public GameFrame(String title, OperationsDone userOperation) {
        super(title);
        this.userOperation = userOperation;
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
        // Get a new graphics context every time through the loop
        // to make sure the strategy is validated

    }
    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(ClientState clientState) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {

                this.g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(g2d, clientState);
                } finally {
                    // Dispose the graphics
                    g2d.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, ClientState state) {
        // Draw background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        drawMap(g2d, state);
        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
                    avg, (currentRender - lastRender));
            g2d.setColor(Color.CYAN);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            int strHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight + 50);
        }
        lastRender = currentRender;
        // Print user guide
        String userGuide
                = "Use the MOUSE or ARROW KEYS to move the BALL. "
                + "Press ESCAPE to end the game.";
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        g2d.drawString(userGuide, 10, GAME_HEIGHT - 10);
        // Draw GAME OVER
        if (userOperation.isGameOver()) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }


    /**
     * menu that renders before game starts
     *
     * @return array with 2 arguments which first argument shows that game is multiPlayer or singlePlayer and
     * second argument shows difficulty level
     */
    public int[] renderMenu() {
        this.g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        int[] userSelectedData = new int[2];
        boolean isMultiPlayer;
        int difficultyLevel;
//        OperationsDone userOperation = state;
        BufferedImage pointer = null;
        try {
            pointer = ImageIO.read(new File("icons/selector.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isMultiPlayer = renderMenuSingleMultiSelection(g2d, userOperation, pointer);
        if (isMultiPlayer) {
            boolean isServer = renderConnectionOptionsMenu(g2d, userOperation, pointer);
            if (isServer) {

            } else {

            }
        }

        difficultyLevel = renderMenuDifficultySelection(g2d, userOperation, pointer);

        if (!isMultiPlayer) {
            userSelectedData[0] = 0;
        } else {
            userSelectedData[0] = 1;
        }
        userSelectedData[1] = difficultyLevel;

        return userSelectedData;
    }

    private boolean renderMenuSingleMultiSelection(Graphics2D g2d, OperationsDone userOperation, BufferedImage pointer) {
        boolean gameModeIsChose = false;
        boolean isMultiPlayer = false;
        // Draw background
        g2d.setColor(Color.cyan);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        String str1 = "Single Player";
        String str2 = "Multi Player";
        int str1Width = g2d.getFontMetrics().stringWidth(str1);
        int str1Height = g2d.getFontMetrics().getHeight();
        int str2Width = g2d.getFontMetrics().stringWidth(str2);
        int str2Height = g2d.getFontMetrics().getHeight();
        //Draw Menu
        g2d.setColor(Color.BLACK);
        g2d.drawString(str1, (GAME_WIDTH - str1Width) / 2, str1Height + 300);
        g2d.drawString(str2, (GAME_WIDTH - str2Width) / 2, str2Height + 450);

        while (!gameModeIsChose) {
            g2d.setColor(Color.BLACK);
            if (userOperation.isKeyDownPressed() || userOperation.isKeyUpPressed())
                isMultiPlayer = !isMultiPlayer;

            if (userOperation.isEnterPressed())
                gameModeIsChose = true;

            if (!isMultiPlayer) {
                g2d.drawImage(pointer, (GAME_WIDTH - str2Width) / 2 - 100, str2Height + 277, null);
                g2d.setColor(Color.cyan);
                g2d.fillRect((GAME_WIDTH - str2Width) / 2 - 100, str2Height + 427, 50, 50);
            } else {
                g2d.drawImage(pointer, (GAME_WIDTH - str2Width) / 2 - 100, str2Height + 427, null);
                g2d.setColor(Color.cyan);
                g2d.fillRect((GAME_WIDTH - str2Width) / 2 - 100, str2Height + 277, 50, 50);
            }


            bufferStrategy.show();
            Toolkit.getDefaultToolkit().sync();


            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isMultiPlayer;
    }

    private int renderMenuDifficultySelection(Graphics2D g2d, OperationsDone userOperation, BufferedImage pointer) {
        boolean gameDifficultyIsChose = false;
        int difficultyLevel = 0;
        // Draw background
        g2d.setColor(Color.cyan);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        String easy = "Easy";
        String normal = "Normal";
        String hard = "Hard";

        int strHeight = g2d.getFontMetrics().getHeight();
        int easyWidth = g2d.getFontMetrics().stringWidth(easy);
        int normalWidth = g2d.getFontMetrics().stringWidth(normal);
        int hardWidth = g2d.getFontMetrics().stringWidth(hard);


        //Draw Menu
        g2d.setColor(Color.BLACK);
        g2d.drawString(easy, (GAME_WIDTH - easyWidth) / 2, strHeight + 200);
        g2d.drawString(normal, (GAME_WIDTH - normalWidth) / 2, strHeight + 300);
        g2d.drawString(hard, (GAME_WIDTH - hardWidth) / 2, strHeight + 400);


        //wait for user to press enter
        while (!gameDifficultyIsChose) {
            g2d.setColor(Color.BLACK);
            if (userOperation.isKeyDownPressed())
                difficultyLevel = ((difficultyLevel + 1) % 3);
            if (userOperation.isKeyUpPressed()) {
                if (difficultyLevel < 0)
                    difficultyLevel += 3;
                else
                    difficultyLevel--;
            }

            if (userOperation.isEnterPressed())
                gameDifficultyIsChose = true;


            //if singleOrMultiPlayer is false it means singlePlayer otherwise its multiPlayer
            if (difficultyLevel == 0) {
                g2d.drawImage(pointer, (GAME_WIDTH - easyWidth) / 2 - 100, strHeight + 177, null);
                g2d.setColor(Color.cyan);
                g2d.fillRect((GAME_WIDTH - normalWidth) / 2 - 100, strHeight + 277, 50, 50);
                g2d.fillRect((GAME_WIDTH - hardWidth) / 2 - 100, strHeight + 377, 50, 50);

            } else if (difficultyLevel == 1) {
                g2d.drawImage(pointer, (GAME_WIDTH - normalWidth) / 2 - 100, strHeight + 277, null);
                g2d.setColor(Color.cyan);
                g2d.fillRect((GAME_WIDTH - easyWidth) / 2 - 100, strHeight + 177, 50, 50);
                g2d.fillRect((GAME_WIDTH - hardWidth) / 2 - 100, strHeight + 377, 50, 50);
            } else {
                g2d.drawImage(pointer, (GAME_WIDTH - hardWidth) / 2 - 100, strHeight + 377, null);
                g2d.setColor(Color.cyan);
                g2d.fillRect((GAME_WIDTH - easyWidth) / 2 - 100, strHeight + 177, 50, 50);
                g2d.fillRect((GAME_WIDTH - normalWidth) / 2 - 100, strHeight + 277, 50, 50);
            }


            bufferStrategy.show();
            Toolkit.getDefaultToolkit().sync();


            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return difficultyLevel;
    }

    private boolean renderConnectionOptionsMenu(Graphics2D g2d, OperationsDone userOperation, BufferedImage pointer) {
        boolean serverOrHostIsChose = false;
        boolean isServer = false;
        // Draw background
        g2d.setColor(Color.cyan);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        String str1 = "Connect To Another Game";
        String str2 = "Make A New Game";
        int str1Width = g2d.getFontMetrics().stringWidth(str1);
        int str1Height = g2d.getFontMetrics().getHeight();
        int str2Width = g2d.getFontMetrics().stringWidth(str2);
        int str2Height = g2d.getFontMetrics().getHeight();
        //Draw Menu
        g2d.setColor(Color.BLACK);
        g2d.drawString(str1, (GAME_WIDTH - str1Width) / 2, str1Height + 300);
        g2d.drawString(str2, (GAME_WIDTH - str2Width) / 2, str2Height + 450);

        while (!serverOrHostIsChose) {
            g2d.setColor(Color.BLACK);
            if (userOperation.isKeyDownPressed() || userOperation.isKeyUpPressed())
                isServer = !isServer;

            if (userOperation.isEnterPressed())
                serverOrHostIsChose = true;

            if (!isServer) {
                g2d.drawImage(pointer, (GAME_WIDTH - str2Width) / 2 - 100, str2Height + 277, null);
                g2d.setColor(Color.cyan);
                g2d.fillRect((GAME_WIDTH - str2Width) / 2 - 100, str2Height + 427, 50, 50);
            } else {
                g2d.drawImage(pointer, (GAME_WIDTH - str2Width) / 2 - 100, str2Height + 427, null);
                g2d.setColor(Color.cyan);
                g2d.fillRect((GAME_WIDTH - str2Width) / 2 - 100, str2Height + 277, 50, 50);
            }


            bufferStrategy.show();
            Toolkit.getDefaultToolkit().sync();


            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isServer;
    }

    private void renderScreenAfterServerSelected() {
//        Socket client = null;
//        try (ServerSocket server = new ServerSocket(7654)) {
//            System.out.print("Server started.\nWaiting for a client ... ");
//            while (client == null) {
//                client = server.accept();
//                System.out.println("client accepted!");
//                pool.execute(new ClientHandler(client));
//            }
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }
//        System.out.println("done.");
//        }
    }

    private void renderScreenAfterClientSelected() {

    }


    private void drawMap(Graphics2D g2d, ClientState state) {
        //get map from game state
        ArrayList<GameObject> map = state.getMap();
        System.out.println(map.size());


        for (int k = 0; k < map.size(); k++) {
            map.get(k).paint(g2d);
        }
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }

}
