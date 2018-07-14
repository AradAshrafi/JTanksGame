package game.gameObjects;

public class DynamicBotTankType1 extends DynamicBotTank{

    public DynamicBotTankType1(int locX, int locY, String pathName, int bulletSpeed){
        super(locX, locY, pathName, bulletSpeed);
        gunType = 0;
    }

}
