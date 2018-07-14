package game.gameSchematic.betweenObjectRelation;

import java.io.Serializable;

public interface LocationsPlacement extends Serializable {

    int getTankLocX();

    int getTankLocY();

    void setTankLocX(int amount);

    void setTankLocY(int amount);

    int getRelativeTankLocX();

    int getRelativeTankLocY();

    void setRelativeTankLocX(int amount);

    void setRelativeTankLocY(int amount);
}
