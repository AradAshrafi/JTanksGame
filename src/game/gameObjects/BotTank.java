package game.gameObjects;

import game.FileOperation.Map;

public class BotTank extends DynamicObject {

    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;
    private Gun tankGun;

    /**
     * @param locX
     * @param locY
     * @param pathName
     * @param bulletSpeed
     */
    public BotTank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
        this.tankGun = new Gun(locX + SIDE_LENGTH / 2, locY + SIDE_LENGTH / 2, "icons/BigEnemyGun.png");
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        super.update(cameraNorthBorder, cameraWestBorder);
        tankGun.update(cameraNorthBorder, cameraWestBorder);
        setRelativeLocX(Math.max(getRelativeLocX(), 0));
        setRelativeLocX(Math.min(getRelativeLocX(), Map.MAP_WIDTH - SIDE_LENGTH));
        setRelativeLocY(Math.max(getRelativeLocY(), 0));
        setRelativeLocY(Math.min(getRelativeLocY(), Map.MAP_HEIGHT - SIDE_LENGTH));
    }



}
