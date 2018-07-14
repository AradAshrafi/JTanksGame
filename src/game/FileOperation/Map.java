package game.FileOperation;

import game.gameObjects.*;
import game.gameSchematic.ThreadPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {

    private final String inputPath = "map.txt";
    private ArrayList<GameObject> mapObjects;
    private ArrayList<GameObject> underLayerObjects;
    private ArrayList<GameObject> upperLayerObjects;
    private ArrayList<GameObject> occupierObjects;
    private ThreadPool dynamicObjectsThreadPool = new ThreadPool();
    private int botCounter = 0;

    public static final int UNIT_PIXELS_NUMBER = 120;
    public static final int MAP_ROWS_NUMBER = 30, MAP_COLUMNS_NUMBER = 11;
    public static final int MAP_WIDTH = MAP_COLUMNS_NUMBER * UNIT_PIXELS_NUMBER, MAP_HEIGHT = MAP_ROWS_NUMBER * UNIT_PIXELS_NUMBER;

    public Map() {
        dynamicObjectsThreadPool.init();
        mapObjects = new ArrayList<>();
        underLayerObjects = new ArrayList<>();
        upperLayerObjects = new ArrayList<>();
        occupierObjects = new ArrayList<>();
    }

    /**
     * read characters from map.txt and build map array
     */
    public void readMap() {
        File file = new File(inputPath);
        try {
            Scanner input = new Scanner(file);
            int counter = 0;
            while (input.hasNextLine()) {
                String[] line = input.nextLine().trim().split("\\s+");

                //build current row of map array
                buildNthRowOfMap(counter, line);
                counter++;
            }

            buildMap();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // print();
    }

    /**
     * build current row of objective map
     *
     * @param row
     * @param characters
     */
    private void buildNthRowOfUnderLayer(int row, String[] characters) {
        for (int j = 0; j < MAP_COLUMNS_NUMBER; j++) {
            switch (characters[j]) {
                case ("W"):
                    Wall wall = new Wall(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    underLayerObjects.add(wall);
                    occupierObjects.add(wall);

                    break;
                case ("G"):
                    Ground ground = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    underLayerObjects.add(ground);
                    break;
                default:
                    Ground groundUnderObjects = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    underLayerObjects.add(groundUnderObjects);
            }
        }
    }

    private void buildNthRowOfUpperLayer(int row, String[] characters) {
        for (int j = 0; j < MAP_COLUMNS_NUMBER; j++) {
            switch (characters[j]) {
                case ("B"):
                    Brick brick = new Brick(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
//                    upperLayerObjects.add(brick);
                    occupierObjects.add(brick);
                    dynamicObjectsThreadPool.execute(brick);

                    break;
                case ("C"):
                    CannonFill cannonFill = new CannonFill(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
//                    upperLayerObjects.add(cannonFill);
                    occupierObjects.add(cannonFill);
                    dynamicObjectsThreadPool.execute(cannonFill);
                    break;
                case ("D"):
                    MachineGunFill machineGunFill = new MachineGunFill(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
//                    upperLayerObjects.add(machineGunFill);
                    occupierObjects.add(machineGunFill);
                    dynamicObjectsThreadPool.execute(machineGunFill);
                    break;
                case ("P"):
                    Plant plant = new Plant(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
//                    upperLayerObjects.add(plant);
                    occupierObjects.add(plant);
                    break;
                case ("U"):
                    GunUpdate cannonUpdate = new GunUpdate(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
//                    upperLayerObjects.add(cannonUpdate);
                    occupierObjects.add(cannonUpdate);
                    dynamicObjectsThreadPool.execute(cannonUpdate);
                    break;
                case ("1"):
                case ("2"):
                case ("4"):
                    Ground tmp = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    upperLayerObjects.add(tmp);
                    occupierObjects.add(tmp);
                    break;

                case ("3"):
                    DynamicBotTankType1 dynamicEnemyTankType1 = new DynamicBotTankType1(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER, "icons/BigEnemy.png", 15);
//                    upperLayerObjects.add(dynamicEnemyTankType1);
                    occupierObjects.add(dynamicEnemyTankType1);
                    dynamicObjectsThreadPool.execute(dynamicEnemyTankType1);
                    break;
            }
        }
    }

    public void buildNthRowOfMap(int row, String[] characters) {

        buildNthRowOfUnderLayer(row, characters);
        buildNthRowOfUpperLayer(row, characters);
    }

    public void buildMap() {
        mapObjects.addAll(underLayerObjects);
        mapObjects.addAll(occupierObjects);
    }


    public ArrayList<GameObject> getMap() {
        return mapObjects;
    }

    public ArrayList<GameObject> getOccupierObjects() {
        return occupierObjects;
    }

    public ThreadPool getDynamicObjectsThreadPool() {
        return dynamicObjectsThreadPool;
    }

    public ArrayList<GameObject> getUnderLayerObjects() {
        return underLayerObjects;
    }
}
