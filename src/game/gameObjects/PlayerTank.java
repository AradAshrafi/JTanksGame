package game.gameObjects;

import game.FileOperation.Map;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerTank extends DynamicObject {

    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;
    private OperationsDone userOperations;
    private BufferedImage cannonGun;
    private BufferedImage machineGun;
    private boolean currentGun;
    private Gun tankGun;

    /**
     * @param locX
     * @param locY
     * @param pathName
     * @param bulletSpeed
     */
    public PlayerTank(int locX, int locY, String pathName, int bulletSpeed, OperationsDone userOperations) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
        this.userOperations = userOperations;
        try {
            this.cannonGun = ImageIO.read(new File("icons/TankCannon.png"));
            this.machineGun = ImageIO.read(new File("icons/tankMachineGun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        super.update(cameraNorthBorder, cameraWestBorder);
//        setRelativeLocX(Math.max(getRelativeLocX(), 0));
//        setRelativeLocX(Math.min(getRelativeLocX(), Map.MAP_WIDTH - SIDE_LENGTH));
//        setRelativeLocY(Math.max(getRelativeLocY(), 0));
//        setRelativeLocY(Math.min(getRelativeLocY(), Map.MAP_HEIGHT - SIDE_LENGTH));
        if (userOperations.isKeyUpPressed()) {
            setLocY(getLocY() - 8);
        }

        if (userOperations.isKeyDownPressed()) {
            setLocY(getLocY() + 8);
        }

        if (userOperations.isKeyLeftPressed()) {
            setLocX(getLocX() - 8);
        }

        if (userOperations.isKeyRightPressed()) {
            setLocX(getLocX() + 8);
        }
    }
}
