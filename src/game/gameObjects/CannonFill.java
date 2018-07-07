package game.gameObjects;

import game.FileOperation.Map;

public class CannonFill extends GameObject implements UpdatableObjects {

    public CannonFill(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        /**
         * updating relative amounts
         */
        setRelativeLocY(getLocY() - cameraNorthBorder/ Map.UNIT_PIXELS_NUMBER *Map.UNIT_PIXELS_NUMBER);
        setRelativeLocX(getLocX() - cameraWestBorder/Map.UNIT_PIXELS_NUMBER *Map.UNIT_PIXELS_NUMBER);

    }
}
