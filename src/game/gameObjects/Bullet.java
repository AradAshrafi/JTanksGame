package game.gameObjects;

public class Bullet extends DynamicObject {
    private float grade;

    public Bullet(int locX, int locY, String pathName, float grade) {
        super(locX, locY, pathName);
        this.grade = grade;
    }

    @Override
    public void update() {

    }
}
