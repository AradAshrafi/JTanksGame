package game.sample.ball;

import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Map {
    private final String inputPath = "map.txt";
    private String[][] map;

    public Map() {
        map = new String[30][11];
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
}
