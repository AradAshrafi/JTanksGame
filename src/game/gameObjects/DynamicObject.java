package game.gameObjects;

public abstract class DynamicObject extends GameObject{

    public DynamicObject(int locX, int locY, String pathName){
        super(locX, locY, pathName);
    }
    public void movementControl(int nextLocX, int nextLocY){

    }

    @Override
    public void update(int cameraNorthBorder, int cameraWestBorder) {
        setRelativeLocY(getLocY() - cameraNorthBorder);
        setRelativeLocX(getLocX() - cameraWestBorder);
    }
}
