package game.gameObjects;

public abstract class Tank extends DynamicObject {
    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;
    private Gun tankGun;
    /**
     * 1 -> West
     * 2 -> North West
     * 3 -> North
     * 4 -> North East
     * 5 -> East
     * 6 -> South East
     * 7 -> South
     * 8 -> South West
     */
    protected int direction;

    public Tank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
        this.direction = 5;
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        super.update(cameraNorthBorder, cameraWestBorder);
//        setLocY(getLocY() + 1);
//        tankGun.update(cameraNorthBorder, cameraWestBorder);
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
    }

    @Override
    public void run() {

    }
}
