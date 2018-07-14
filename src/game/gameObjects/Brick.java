package game.gameObjects;

public class Brick extends DamageableObjec {
    private final int SIDE_LENGTH = 120;
    public Brick(int locX, int locY) {
        super(locX, locY, "icons/Brick.png");
        setSideLength(120);
        health = 9;
    }
    public void getUnitDamaged(){
        health --;
    }

    public void update(int cameraNorthBorder, int cameraWestBorder) {

    }
}
