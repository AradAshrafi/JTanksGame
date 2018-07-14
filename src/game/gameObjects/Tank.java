package game.gameObjects;

public abstract class Tank extends RemovableObject {
    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;
    protected int gunType = 1;

    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
        this.direction = 5;
        setSideLength(100);
    }

    public void getUnitDamaged(){
        health --;
    }

}
