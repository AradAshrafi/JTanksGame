package game.gameSchematic.betweenObjectRelation;

public interface OperationsDone {

    boolean isKeyUpPressed();

    boolean isKeyDownPressed();

    boolean isKeyRightPressed();

    boolean isKeyLeftPressed();

    boolean isKeyUp2Pressed();

    boolean isKeyDown2Pressed();

//    boolean isKeyLeft2Pressed();
//
//    boolean isKeyRight2Pressed();


    int getRelativeMouseX();

    int getRelativeMouseY();

    int getMouseX();

    int getMouseY();

    boolean isMousePressed();

    boolean isMouseRightPressed();

    void setMouseRightPressed(boolean sth);

    void setMouseLeftPressed(boolean sth);

    boolean isMouseLeftPressed();

    void setMousePressed(boolean sth);

    boolean isMouseMoved();

    boolean isGameOver();

    boolean isEnterPressed();

    void setMouseMoved(boolean sth);

    boolean getMouseMoved();

}
