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
    static final int WIDTH = 11;
    static final int HEIGHT = 30;

    public Map() {
        map = new String[HEIGHT][WIDTH];
    }

    public void readMap() {
        File file = new File(inputPath);
        String[][] reversedMap = new String[HEIGHT][WIDTH];
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

    public void print(){
        for(int i =0; i<30; i++){
            for(int j = 0; j<11; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
