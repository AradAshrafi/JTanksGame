package game.gameObjects;

public abstract class StaticObject extends GameObject {

    public StaticObject(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    public void update(int cameraNorthBorder, int cameraWestBorder) {
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
    }
}
