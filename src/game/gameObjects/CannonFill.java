package game.gameObjects;

public class CannonFill extends GameObject implements UpdatableObjects {

    public CannonFill(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        /**
         * updating relative amounts
         */
        setRelativeLocY((getLocY() - cameraNorthBorder) / 120 * 120);
        setRelativeLocX(((getLocX() - cameraWestBorder) / 120) * 120);

    }
}
