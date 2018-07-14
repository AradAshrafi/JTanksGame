package game.gameSchematic;

import game.gameObjects.*;
import game.gameSchematic.betweenObjectRelation.LocationsPlacement;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.util.ArrayList;
import java.util.Iterator;

public class ClientState implements LocationsPlacement {
    private ArrayList<GameObject> map;
    private ArrayList<GameObject> mapOccupierObjects;
    private ArrayList<GameObject> underLayerObject;
    private ThreadPool dynamicObjectsThreadPool;
    private PlayerTank playerTank;
    private UserOperation userOperation;
    private ClientCamera camera;

    public ClientState(UserOperation userOperation, ArrayList<GameObject> underLayerObject, ArrayList<GameObject> mapOccupierObjects, ThreadPool dynamicObjectsThreadPool, int playerTankLocX, int playerTankLocY) {
        this.underLayerObject = underLayerObject;
        this.userOperation = userOperation;
        this.mapOccupierObjects = mapOccupierObjects;
        this.dynamicObjectsThreadPool = dynamicObjectsThreadPool;
        dynamicObjectsThreadPool.init();
        this.playerTank = new PlayerTank(playerTankLocX, playerTankLocY, "icons/PlayerTank.png", 20, (OperationsDone) (userOperation), (LocationsPlacement) this);
        this.camera = new ClientCamera((LocationsPlacement) (this), (OperationsDone) (userOperation));
        dynamicObjectsThreadPool.execute(playerTank);
//        map.add(playerTank);
        mapOccupierObjects.add(playerTank);
//        mapOccupierObjects.add(playerTank);
    }

    /**
     * The method which updates the game state.
     */
    public void update() {

        // cheatCode.update(kE);
        camera.update();

        if (userOperation.isMouseLeftPressed()) {
            updateGameObjects(camera.getCameraNorthBorder(), camera.getCameraWestBorder(), mapOccupierObjects);
            dynamicObjectsThreadPool.init();
            playerTank.shot(mapOccupierObjects, dynamicObjectsThreadPool, camera.getCameraWestBorder() + userOperation.getRelativeMouseX(), camera.getCameraNorthBorder() + userOperation.getRelativeMouseY());
            //System.out.println(playerTank.getLocX() + "  " + playerTank.getLocY() + "  " + (getCameraWestBorder() + relativeMouseX) + "  " + (getCameraNorthBorder() + relativeMouseY) + "  ");
            //mapOccupierObjects.add(newCannonBullet);
            userOperation.setMouseLeftPressed(false);
        }
        if (userOperation.isMouseRightPressed()) {
            playerTank.changeGunType();
            userOperation.setMouseRightPressed(false);
        }

        camera.update();
        // cameraAndMyTank.updateByMouse();
        updateGameObjects(camera.getCameraNorthBorder(), camera.getCameraWestBorder(), mapOccupierObjects);
    }


    private void updateGameObjects(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) {

        Iterator<GameObject> it2 = underLayerObject.iterator();
        while (it2.hasNext()) {
            GameObject currentObject = it2.next();

            if (currentObject instanceof RemovableObject)
                currentObject.update(cameraNorthBorder, cameraWestBorder, occupierObjects);
            else
                currentObject.update(cameraNorthBorder, cameraWestBorder);
        }

//        Iterator<GameObject> it = occupierObjects.iterator();
//        while (it.hasNext()) {
        int occupierSize = occupierObjects.size();
        for (int i = 0; i < occupierSize; i++) {
            GameObject currentObject = occupierObjects.get(i);
            //TODO: will be completed in future to remove bullet if its passing forbidden part of map
//            if(currentObject instanceof Bullet){
//                if(currentObject.getLocX() || currentObject.getLocY() )
//            }
            if (currentObject instanceof RemovableObject) {
                if (currentObject instanceof DynamicBotTank) {
                    DynamicBotTank currentBotTank = (DynamicBotTank) currentObject;
                    currentBotTank.update(cameraNorthBorder, cameraWestBorder, occupierObjects, dynamicObjectsThreadPool, this);
                } else
                    currentObject.update(cameraNorthBorder, cameraWestBorder, occupierObjects);
            } else
                currentObject.update(cameraNorthBorder, cameraWestBorder);
        }

//        }
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

//    @Override
//    public void setTankLocX(int amount) {
//        playerTank.setLocX(amount);
//    }
//
//    @Override
//    public void setTankLocY(int amount) {
//        playerTank.setLocY(amount);
//    }
//
//    @Override
//    public int getRelativeTankLocX() {
//        return playerTank.getRelativeLocX();
//    }
//
//    @Override
//    public int getRelativeTankLocY() {
//        return playerTank.getRelativeLocY();
//    }
//
//    @Override
//    public void setRelativeTankLocX(int amount) {
//        playerTank.setRelativeLocX(amount);
//    }
//
//    @Override
//    public void setRelativeTankLocY(int amount) {
//        playerTank.setRelativeLocY(amount);
//    }

    public ArrayList<GameObject> getMapOccupierObjects() {
        return mapOccupierObjects;
    }

    public ArrayList<GameObject> getUnderLayerObject() {
        return underLayerObject;
    }

    public ThreadPool getDynamicObjectsThreadPool() {
        return dynamicObjectsThreadPool;
    }

}
