package game.gameObjects;

public class Wall extends NoneRemovableObject {
    public Wall(int locX, int locY) {
        super(locX, locY, "icons/Wall.png");
    }
}
