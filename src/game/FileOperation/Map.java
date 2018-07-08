package game.FileOperation;

import game.gameObjects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private final String inputPath = "map.txt";
    private GameObject[][] map;

    //things like brick,prizes and ... -->
    private ArrayList<UpdatableObjects> itemsInMap;
    //<--

    public static final int UNIT_PIXELS_NUMBER = 120;
    private static final int MAP_ROWS_NUMBER = 30, MAP_COLUMNS_NUMBER = 11;
    public static final int MAP_WIDTH = MAP_COLUMNS_NUMBER * UNIT_PIXELS_NUMBER, MAP_HEIGHT = MAP_ROWS_NUMBER * UNIT_PIXELS_NUMBER;

    public Map() {
        map = new GameObject[MAP_ROWS_NUMBER][MAP_COLUMNS_NUMBER];
        itemsInMap = new ArrayList<>();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * build current row of objective map
     *
     * @param row
     * @param characters
     */
    private void buildNthRowOfMap(int row, String[] characters) {
        for (int j = 0; j < MAP_COLUMNS_NUMBER; j++) {
            switch (characters[j]) {
                case ("W"):
                    Wall wall = new Wall(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    map[row][j] = wall;
                    break;
                case ("G"):
                    Ground ground = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    map[row][j] = ground;
                    break;
                case ("B"):
                    Ground groundUnderBrick = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    Brick brick = new Brick(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    map[row][j] = groundUnderBrick;
                    itemsInMap.add(brick);
                    break;
                case ("C"):
                    Ground groundUnderCannonFill = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    CannonFill cannonFill = new CannonFill(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    map[row][j] = groundUnderCannonFill;
                    itemsInMap.add(cannonFill);
                    break;
                case ("D"):
                    Ground groundUnderMachineGunFill = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    MachineGunFill machineGunFill = new MachineGunFill(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    map[row][j] = groundUnderMachineGunFill;
                    itemsInMap.add(machineGunFill);
                    break;
                case ("P"):
                    Plant plant = new Plant(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    map[row][j] = plant;
                    break;
                case ("U"):
                    CannonUpdate cannonUpdate = new CannonUpdate(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    Ground groundUnderCannonUpdate = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    map[row][j] = groundUnderCannonUpdate;
                    itemsInMap.add(cannonUpdate);
                    break;
                case ("1"):
                    break;
                case ("2"):
                    break;
                case ("3"):
                    Ground groundUnderEnemyTank = new Ground(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER);
                    Tank enemyTank = new Tank(j * UNIT_PIXELS_NUMBER, row * UNIT_PIXELS_NUMBER, "icons/BigEnemy.png", 15);
                    map[row][j] = groundUnderEnemyTank;
                    itemsInMap.add(enemyTank);
                    break;
                case ("4"):
                    break;
            }
        }
    }


    public GameObject[][] getMap() {
        return map;
    }

    public ArrayList<UpdatableObjects> getItemsInMap() {
        return itemsInMap;
    }

}
