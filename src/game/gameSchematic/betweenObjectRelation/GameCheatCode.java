package game.gameSchematic.betweenObjectRelation;

import java.awt.event.KeyEvent;

public class GameCheatCode {
    final int size = 4;
    private String[] cheatCodes = {"littleshit", "demonnun", "likeaboss", "fastandfurious"};
    private boolean cheatMode;
    private int currentCheatCodeNumber =0;
    private String inputCode = "";

    public GameCheatCode(){
        cheatMode = false;
    }

    public void setCheatMode(KeyEvent e){
        if (e.isAltDown() && e.getKeyChar() == 'x'){
            if (cheatMode) cheatMode = false;
            else cheatMode = true;
        }
    }

    public void setInputCode(KeyEvent e){
        inputCode += e.getKeyChar();
        System.out.println(inputCode);
    }


    public void setCurrentCheatCodeNumber(){
        currentCheatCodeNumber =-1;
        for (int i =0; i<4; i++){
            if (inputCode.equals(cheatCodes[i])){
                currentCheatCodeNumber = i;
                break;
            }
        }
    }

    public void applyCheatCode(int currentCheatCodeNumber){
        switch (currentCheatCodeNumber){
            case 0:

                System.out.println(currentCheatCodeNumber);
                break;
            case 1:
                System.out.println(currentCheatCodeNumber);
                break;
            case 2:
                System.out.println(currentCheatCodeNumber);
                break;
            case 3:
                System.out.println(currentCheatCodeNumber);
                break;
            case -1:
                System.out.println(currentCheatCodeNumber);
                break;
        }
    }

    public void update(KeyEvent e){
        setCheatMode(e);
        if (cheatMode){
            setInputCode(e);
            setCurrentCheatCodeNumber();
            applyCheatCode(currentCheatCodeNumber);
        }
    }
}
