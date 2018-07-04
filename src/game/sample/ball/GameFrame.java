package game.sample.ball;

import java.awt.*;
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
    private Map map;

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle
    private BufferedImage tankImage;
    private BufferedImage wallImage;
    private BufferedImage groundImage;
    private BufferedImage notDamagedBrickImage;
    private BufferedImage damagedBrickImage;

    private long lastRender;
    private ArrayList<Float> fpsHistory;
    private BufferStrategy bufferStrategy;

    public GameFrame(String title) {
        super(title);
        map = new Map();
        map.readMap();

        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);

        try {
            tankImage = ImageIO.read(new File("Icon.png"));
            wallImage = ImageIO.read(new File("icons/Wall.png"));
            groundImage = ImageIO.read(new File("icons/Soil.png"));
            notDamagedBrickImage = ImageIO.read(new File("icons/Brick.png"));
            damagedBrickImage = ImageIO.read(new File("icons/DamagedBrick.png"));

        } catch (IOException e) {
            System.out.println(e);
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


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
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
    private void doRendering(Graphics2D g2d, GameState state) {
        // Draw background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        // Draw ball
        g2d.setColor(Color.BLACK);
//        g2d.fillOval(state.locX, state.locY, state.diam, state.diam);
        drawMap(g2d, state);
        drawDynamics(g2d,state);

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
        if (state.gameOver) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

    private void drawMap(Graphics2D g2d, GameState state) {
        int myTankLocX = state.locX;
        int myTankLocY = state.locY;
        String[][] map = this.map.getMap();
        for (int i = myTankLocY / 120; i < myTankLocY / 120 + 6; i++) {
            for (int j = myTankLocX / 120; j < myTankLocX / 120 + 8; j++) {
                if (j == 11)
                    break;
                switch (map[i][j]) {
                    case ("W"):
                        g2d.drawImage(wallImage, j * 120, i * 120, null);
                        break;
                    case ("G"):
                        g2d.drawImage(groundImage, j * 120, i * 120, null);
                        break;
                    case ("B"):
                        g2d.drawImage(wallImage, j * 120, i * 120, null);
                        break;
                    case ("D"):
                        g2d.drawImage(wallImage, j * 120, i * 120, null);
                        break;
                }
            }
            if (i == 30)
                break;
        }
    }

    private void drawDynamics(Graphics2D g2d, GameState state){
        g2d.drawImage(tankImage, state.locX, state.locY, null);

    }

}
