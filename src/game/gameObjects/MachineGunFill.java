package game.gameObjects;

import game.FileOperation.Map;

public class MachineGunFill extends GameObject implements UpdatableObjects {
    public MachineGunFill(int locX, int locY) {
        super(locX, locY, "icons/MachineGunFood.png");
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
