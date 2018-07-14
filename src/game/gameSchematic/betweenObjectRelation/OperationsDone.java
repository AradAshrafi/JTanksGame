package game.gameSchematic.betweenObjectRelation;

import java.io.Serializable;

public interface OperationsDone extends Serializable {

    boolean isKeyUpPressed();

    boolean isKeyDownPressed();

    boolean isKeyRightPressed();

    boolean isKeyLeftPressed();

    int getRelativeMouseX();

    int getRelativeMouseY();

    boolean isMousePressed();

    void setMousePressed(boolean sth);

    boolean isMouseMoved();

    boolean isGameOver();

    boolean isEnterPressed();

    void setMouseMoved(boolean sth);

    boolean getMouseMoved();

}
