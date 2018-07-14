package game.gameObjects;

public class CannonUpdate extends RemovableObject {
    public CannonUpdate(int locX, int locY) {
        super(locX, locY, "icons/CannonUpdate.png");
        setSideLength(80);
        health = 1;
    }

    public void getUnitDamaged() {
        health--;
    }
}
