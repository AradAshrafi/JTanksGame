package game.gameObjects;

import game.FileOperation.Map;
//import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GameObject {

    private int locX;
    private int locY;
    private int relativeLocX;
    private int relativeLocY;
    private int sideLength;
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

    /**
     * for non smart objects we set their relative position directly* --- for smart ones we set their relative position with their update method (check SmartObject Class for more details) ---
     *
     * @param g2d
     */
    public void paint(Graphics2D g2d) {
        if (relativeLocX > (0 - Map.UNIT_PIXELS_NUMBER) && relativeLocX < Map.MAP_WIDTH
                && relativeLocY > (0 - Map.UNIT_PIXELS_NUMBER) && relativeLocY < Map.MAP_HEIGHT)
            g2d.drawImage(this.getObjectImage(), this.getRelativeLocX(), this.getRelativeLocY(), null);
    }


    public void update(int cameraNorthBorder, int cameraWestBorder) { }
    public void update(int cameraNorthBorder, int cameraWestBorder, ArrayList<GameObject> occupierObjects) { }

    /*
    protected void resizeImage(int newW, int newH) {
        try {
            objectImage = Thumbnails.of(this.objectImage).forceSize(newW, newH).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    public int getSideLength(){
        return sideLength;
    }

    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }
}
