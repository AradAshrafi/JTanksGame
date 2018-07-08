package game.gameObjects;

import java.awt.*;

public interface UpdatableObjects {
    void update(int cameraNorthBorder, int cameraWestBorder);

    void paint(Graphics2D g2d);
}
