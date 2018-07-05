package game.gameObjects;

public class Tank extends DynamicObject {

    private int bulletSpeed;

    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
    }


    @Override
    public void update() {

    }
}
