package game.gameObjects;

public abstract class Tank extends DynamicObject {
    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;
    private Gun tankGun;


    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
        this.direction = 5;
    }

}
