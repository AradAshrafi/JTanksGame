package game.gameSchematic;

import game.FileOperation.Map;
import game.gameObjects.*;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;


import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientState implements LocationsPlacement {
    private ArrayList<GameObject> underLayerObjects;
    private ArrayList<GameObject> mapOccupierObjects;
//    private ArrayList<GameObject> upperLayerObjects;

    //    private transient ThreadPool dynamicObjectsThreadPool;
    private static PlayerTank playerTank;
    private static OperationsDone userOperation;
    private static ClientCamera camera;


    public ClientState(OperationsDone userOperation, ArrayList<GameObject> underLayerObjects, ArrayList<GameObject> mapOccupierObjects, ThreadPool dynamicObjectsThreadPool, int playerTankLocX, int playerTankLocY) {
//        this.map = map;
//        System.out.println("map init size: " + mapOccupierObjects.size());
//        this.dynamicObjectsThreadPool = dynamicObjectsThreadPool;
        this.userOperation = userOperation;
        this.underLayerObjects = underLayerObjects;
//        this.upperLayerObjects = upperLayerObjects;
        this.mapOccupierObjects = mapOccupierObjects;


        this.playerTank = new PlayerTank(playerTankLocX, playerTankLocY, "icons/PlayerTank.png", 20, (userOperation), (LocationsPlacement) this);
        camera = new ClientCamera((LocationsPlacement) (this), (OperationsDone) (userOperation), playerTank);
//        dynamicObjectsThreadPool.execute(playerTank);

        this.mapOccupierObjects.add(playerTank);
        System.out.println("map final size " + mapOccupierObjects.size());
    }

    /**
     * The method which updates the game state.
     */
    public void update() {

        System.out.println(userOperation);
//        playerTank.setUserOperations(userOperation);
//         cheatCode.update(kE);
        if (userOperation.isMousePressed()) {
            CannonBullet newCannonBullet = new CannonBullet(playerTank.getLocX(), playerTank.getLocY(),
                    camera.getCameraWestBorder() + userOperation.getRelativeMouseX(), camera.getCameraNorthBorder() + userOperation.getRelativeMouseY(), 20);
//            System.out.println(playerTank.getLocX() + "  " + playerTank.getLocY() + "  " + (getCameraWestBorder() + relativeMouseX) + "  " + (getCameraNorthBorder() + relativeMouseY) + "  ");
            mapOccupierObjects.add(newCannonBullet);
//            upperLayerObjects.add(newCannonBullet);
            userOperation.setMousePressed(false);
        }
        camera.update();
        // cameraAndMyTank.updateByMouse();
        updateGameObjects(camera.getCameraNorthBorder(), camera.getCameraWestBorder());
        camera.update();
    }


    private void updateGameObjects(int cameraNorthBorder, int cameraWestBorder) {
        Iterator<GameObject> it = underLayerObjects.iterator();
        while (it.hasNext()) {
            GameObject currentObject = it.next();
            currentObject.update(cameraNorthBorder, cameraWestBorder);

        }
        //TODO: will be completed in future to remove bullet if its passing forbidden part of map
//            if(currentObject instanceof Bullet){
//                if(currentObject.getLocX() || currentObject.getLocY() )
//            }

//        playerTank.setUserOperations(userOperation);
        it = mapOccupierObjects.iterator();
        while (it.hasNext()) {
            GameObject currentObject = it.next();
            if (!(currentObject instanceof DynamicObject))
                currentObject.update(cameraNorthBorder, cameraWestBorder);
            currentObject.update(cameraNorthBorder, cameraWestBorder, mapOccupierObjects);
        }

    }

    public void setMapOccupierObjects(ArrayList<GameObject> mapOccupierObjects) {
        this.mapOccupierObjects = mapOccupierObjects;
    }

    public ArrayList<GameObject> getUnderLayerObjects() {
        return underLayerObjects;
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

    public static ClientCamera getCamera() {
        return camera;
    }
//    public ThreadPool getDynamicObjectsThreadPool() {
//        return dynamicObjectsThreadPool;
//    }

}
