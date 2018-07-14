package game.gameObjects;

public class MachineGunBullet extends Bullet {
    public MachineGunBullet(int locX, int locY, int aimPositionX, int aimPositionY, int speed) {
        super(locX, locY, "icons/LightBullet.png", aimPositionX, aimPositionY, speed);
    }

}
