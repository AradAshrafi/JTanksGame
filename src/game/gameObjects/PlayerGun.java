package game.gameObjects;

public class PlayerGun extends GameObject implements UpdatableObjects {
    public PlayerGun(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }


    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {

    }
}
