package game.gameSchematic.betweenObjectRelation;

public interface OperationsDone {

    boolean isKeyUpPressed();

    boolean isKeyDownPressed();

    boolean isKeyRightPressed();

    boolean isKeyLeftPressed();

    boolean isMouseMoved();

    boolean isEnterPressed();

    void setMouseMoved(boolean sth);

    boolean getMouseMoved();

}
