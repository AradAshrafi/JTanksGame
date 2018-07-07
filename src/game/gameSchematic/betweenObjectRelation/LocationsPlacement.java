package game.gameSchematic.betweenObjectRelation;

public interface LocationsPlacement {
    int getMouseLocX();

    int getMouseLocY();

    int getTankLocX();

    int getTankLocY();

    void setTankLocX(int amount);

    void setTankLocY(int amount);

    int getRelativeTankLocX();

    int getRelativeTankLocY();

    void setRelativeTankLocX(int amount);

    void setRelativeTankLocY(int amount);
}
