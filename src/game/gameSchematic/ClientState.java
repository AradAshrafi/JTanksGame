package game.gameSchematic;

import game.FileOperation.Map;
import game.gameObjects.CannonBullet;
import game.gameObjects.DynamicObject;
import game.gameObjects.GameObject;
import game.gameObjects.PlayerTank;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientState implements LocationsPlacement {
    private ArrayList<GameObject> map;
    private ArrayList<GameObject> mapOccupierObjects;
    private ThreadPool dynamicObjectsThreadPool;
    private PlayerTank playerTank;
    private UserOperation userOperation;
    private ClientCamera camera;

    public ClientState(UserOperation userOperation, ArrayList<GameObject> map, ArrayList<GameObject> mapOccupierObjects, ThreadPool dynamicObjectsThreadPool, int playerTankLocX, int playerTankLocY) {
        this.map = map;
        this.userOperation = userOperation;
        this.mapOccupierObjects = mapOccupierObjects;
        this.playerTank = new PlayerTank(playerTankLocX, playerTankLocY, "icons/PlayerTank.png", 20, (OperationsDone) (userOperation), (LocationsPlacement) this);
        this.camera = new ClientCamera((LocationsPlacement) (this), (OperationsDone) (userOperation));
        dynamicObjectsThreadPool.execute(playerTank);
        map.add(playerTank);
    }

    /**
     * The method which updates the game state.
     */
    public void update() {

        // cheatCode.update(kE);
        if (userOperation.isMousePressed()) {
            System.out.println("tiir");
            CannonBullet newCannonBullet = new CannonBullet(playerTank.getLocX(), playerTank.getLocY(),
                    camera.getCameraWestBorder() + userOperation.getRelativeMouseX(), camera.getCameraNorthBorder() + userOperation.getRelativeMouseY(), 20);
//            System.out.println(playerTank.getLocX() + "  " + playerTank.getLocY() + "  " + (getCameraWestBorder() + relativeMouseX) + "  " + (getCameraNorthBorder() + relativeMouseY) + "  ");
            map.add(newCannonBullet);
            userOperation.setMousePressed(false);
        }
        ;
        camera.update();
        // cameraAndMyTank.updateByMouse();
        updateGameObjects(camera.getCameraNorthBorder(), camera.getCameraWestBorder(), mapOccupierObjects);
    }


    private void updateGameObjects(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {
        Iterator<GameObject> it = map.iterator();
        while (it.hasNext()) {
            GameObject currentObject = it.next();
            //TODO: will be completed in future to remove bullet if its passing forbidden part of map
//            if(currentObject instanceof Bullet){
//                if(currentObject.getLocX() || currentObject.getLocY() )
//            }
            if (currentObject instanceof DynamicObject)
                currentObject.update(cameraNorthBorder, cameraWestBorder, occupierObjects);
            currentObject.update(cameraNorthBorder, cameraWestBorder);

        }
    }


    public ArrayList<GameObject> getMap() {
        return map;
    }

    public PlayerTank getPlayerTank() {
        return playerTank;
    }


    @Override
    public int getTankLocX() {
        return playerTank.getLocX();
    }

    @Override
    public int getTankLocY() {
        return playerTank.getLocY();
    }

    @Override
    public void setTankLocX(int amount) {
        playerTank.setLocX(amount);
    }

    @Override
    public void setTankLocY(int amount) {
        playerTank.setLocY(amount);
    }

    @Override
    public int getRelativeTankLocX() {
        return playerTank.getRelativeLocX();
    }

    @Override
    public int getRelativeTankLocY() {
        return playerTank.getRelativeLocY();
    }

    @Override
    public void setRelativeTankLocX(int amount) {
        playerTank.setRelativeLocX(amount);
    }

    @Override
    public void setRelativeTankLocY(int amount) {
        playerTank.setRelativeLocY(amount);
    }

    public ArrayList<GameObject> getMapOccupierObjects() {
        return mapOccupierObjects;
    }

    public ThreadPool getDynamicObjectsThreadPool() {
        return dynamicObjectsThreadPool;
    }

}
