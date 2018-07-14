package game.gameObjects;

public class MachineGunFill extends RemovableObject {
    public MachineGunFill(int locX, int locY) {
        super(locX, locY, "icons/MachineGunFood.png");
        setSideLength(80);
        health = 1;
    }

    public void getUnitDamaged() {
        health--;
    }
}
