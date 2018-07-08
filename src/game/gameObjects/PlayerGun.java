package game.gameObjects;

import game.FileOperation.Map;

public class PlayerGun extends GameObject implements UpdatableObjects {
    public PlayerGun(int locX, int locY) {
        super(locX, locY, "icons/TankCannon.png");
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        /**
         * updating relative amounts
         */
        setRelativeLocY(getLocY() - cameraNorthBorder / Map.UNIT_PIXELS_NUMBER * Map.UNIT_PIXELS_NUMBER);
        setRelativeLocX(getLocX() - cameraWestBorder / Map.UNIT_PIXELS_NUMBER * Map.UNIT_PIXELS_NUMBER);
    }
}
