package game.gameObjects;

import game.FileOperation.Map;

public class Plant extends GameObject implements UpdatableObjects {

    public Plant(int locX, int locY) {
        super(locX, locY, "icons/Plant.png");
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