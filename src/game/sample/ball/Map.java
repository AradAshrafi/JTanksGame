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
        String[][] reversedMap = new String[30][11];
        try {
            Scanner input = new Scanner(file);
            int counter = 0;
            while (input.hasNextLine()) {
                String[] line = input.nextLine().trim().split("\\s+");
                reversedMap[counter] = line;
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //because mamad rid :))
        for (int i = 0; i < 30; i++) {
            map[i] = reversedMap[29 - i];
        }
    }

    public String[][] getMap() {
        return map;
    }
}
