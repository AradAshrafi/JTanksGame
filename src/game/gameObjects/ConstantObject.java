package game.gameObjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConstantObject {
    private int locX;
    private int locY;
    private BufferedImage objectImage;

    public ConstantObject(int locX, int locY, String pathName) {
        this.locX = locX;
        this.locY = locY;
        try {
            this.objectImage = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }
    
}
