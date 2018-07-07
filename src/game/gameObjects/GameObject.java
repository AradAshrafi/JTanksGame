package game.gameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class GameObject {
    private int locX;
    private int locY;
    private int relativeLocX;
    private int relativeLocY;
    private BufferedImage objectImage;


    public GameObject(int locX, int locY, String pathName) {
        this.locX = locX;
        this.locY = locY;
        this.relativeLocX = -1;
        this.relativeLocY = -1;
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

    public int getRelativeLocX() {
        return relativeLocX;
    }

    public void setRelativeLocX(int relativeLocX) {
        this.relativeLocX = relativeLocX;
    }

    public int getRelativeLocY() {
        return relativeLocY;
    }

    public void setRelativeLocY(int relativeLocY) {
        this.relativeLocY = relativeLocY;
    }

    public BufferedImage getObjectImage() {
        return objectImage;
    }


    public abstract void update(int cameraNorthBorder, int cameraWestBorder);

    public abstract void paint(Graphics2D g2d);
}
