package game.gameObjects;


public class BotTank extends DynamicObject {

    private int bulletSpeed;
    private final int SIDE_LENGTH = 120;
    private Gun tankGun;

    /**
     * @param locX
     * @param locY
     * @param pathName
     * @param bulletSpeed
     */
    public BotTank(int locX, int locY, String pathName, int bulletSpeed) {
        super(locX, locY, pathName);
        this.bulletSpeed = bulletSpeed;
        this.tankGun = new Gun(locX + SIDE_LENGTH / 2, locY + SIDE_LENGTH / 2, "icons/BigEnemyGun.png");
    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        super.update(cameraNorthBorder, cameraWestBorder);
        setLocY(getLocY() + 1);
        tankGun.update(cameraNorthBorder, cameraWestBorder);
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
    }
    @Override
    public void run(){
//        System.out.println("hellooooooooooooooooooooooooooo");
//        try {
//            while (true) {
//                long start = System.currentTimeMillis();
//                long delay = (15 - (System.currentTimeMillis() - start));
//                if (delay > 0) {
//                    Thread.sleep(delay);
//                    System.out.println("Hello");
//                }
//            }
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
    }
}
