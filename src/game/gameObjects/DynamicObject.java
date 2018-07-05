package game.gameObjects;

public abstract class DynamicObject extends ConstantObject {
    public DynamicObject(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    public abstract void update();
}
