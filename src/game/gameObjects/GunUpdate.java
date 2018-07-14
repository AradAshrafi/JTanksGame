package game.gameObjects;

public class GunUpdate extends RemovableObject {
    public GunUpdate(int locX, int locY) {
        super(locX, locY, "icons/GunUpdate.png");
        setSideLength(80);
        health = 1;
    }

    public void getUnitDamaged() {
        health--;
    }
}
