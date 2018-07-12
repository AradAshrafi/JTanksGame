package game.gameObjects;


public class BotTank extends Tank {
    public BotTank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName, bulletSpeed);
    }

    //    @Override
//    public void update(int cameraNorthBorder, int cameraWestBorder) {
//        super.update(cameraNorthBorder, cameraWestBorder);
//        setLocY(getLocY() + 1);
//        tankGun.update(cameraNorthBorder, cameraWestBorder);
//        setRelativeLocY(getLocY() - cameraNorthBorder);
//        setRelativeLocX(getLocX() - cameraWestBorder);
//    }
    @Override
    public void run() {

    }
}
