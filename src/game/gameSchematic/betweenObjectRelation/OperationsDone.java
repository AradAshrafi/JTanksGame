package game.gameSchematic.betweenObjectRelation;

public interface OperationsDone {
    boolean isKeyUpPressed();

    boolean isKeyDownPressed();

    boolean isKeyRightPressed();

    boolean isKeyLeftPressed();

    boolean isMouseMoved();

    void setMouseMoved(boolean sth);

    boolean getMouseMoved();

}
