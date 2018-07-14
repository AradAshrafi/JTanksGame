package game.gameObjects;

public class DynamicBotTankType2 extends DynamicBotTank {

    public DynamicBotTankType2(int locX, int locY, String pathName, int bulletSpeed){
        super(locX, locY, pathName, bulletSpeed);
        gunType = 1;
    }

}