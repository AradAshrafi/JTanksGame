package game.gameObjects;

public class CannonFill extends RemovableObject {

    public CannonFill(int locX, int locY) {
        super(locX, locY, "icons/CannonFood.png");
        setSideLength(80);
        health = 1;
    }
    public void getUnitDamaged(){
        health --;
    }

}
