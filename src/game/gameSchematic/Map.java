package game.gameSchematic;

import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Map {
    private final String inputPath = "map.txt";
    private String[][] map;
    static final int UNIT_PIXELS_NUMBER = 120;
    static final int MAP_COLUMNS_NUMBER = 11, MAP_ROWS_NUMBER = 30;
    static final int MAP_WIDTH = MAP_COLUMNS_NUMBER * UNIT_PIXELS_NUMBER, MAP_HEIGHT = MAP_ROWS_NUMBER * UNIT_PIXELS_NUMBER;

    public Map() {
        map = new String[MAP_ROWS_NUMBER][MAP_COLUMNS_NUMBER];
    }

    public void readMap() {
        File file = new File(inputPath);
        try {
            Scanner input = new Scanner(file);
            int counter = 0;
            while (input.hasNextLine()) {
                String[] line = input.nextLine().trim().split("\\s+");
                map[counter] = line;
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String[][] getMap() {
        return map;
    }

    public void print() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
