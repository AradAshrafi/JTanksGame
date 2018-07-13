package game.gameObjects;


import java.util.ArrayList;

public class BotTank extends Tank {
    private int botHealth = 3;
    public BotTank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName, bulletSpeed);
        health = botHealth;
    }
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects){
        super.update(cameraNorthBorder, cameraWestBorder, occupierObjects);

    }

    //    @Override
//    public void update(int cameraNorthBorder, int cameraWestBorder) {
//        super.update(cameraNorthBorder, cameraWestBorder);
//        tankGun.update(cameraNorthBorder, cameraWestBorder);
//        setRelativeLocY(getLocY() - cameraNorthBorder);
//        setRelativeLocX(getLocX() - cameraWestBorder);
//    }

}
