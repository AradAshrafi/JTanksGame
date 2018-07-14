package game.gameObjects;

import java.util.ArrayList;

public class Gun extends RemovableObject {
    protected int aimLocX, aimLocY;
    protected int deltaX, deltaY;
    protected double degree = 0;

    public Gun(int locX, int locY, String pathName) {
        super(locX, locY, pathName);
    }

    public void update(int cameraNorthBorder, int cameraWestBorder, int tankLocX, int tankLocY
            , int tankLength, ArrayList<GameObject> occupierObjects, int aimRelativeLocX, int aimRelativeLocY) {
    }
}
