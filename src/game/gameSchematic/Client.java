package game.gameSchematic;

import game.FileOperation.Map;
import game.gameObjects.GameObject;
import game.gameSchematic.betweenObjectRelation.OperationsDone;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Runnable {
    private int clientPort;
    private ClientState state;
    private GameFrame canvas;
    private OperationsDone userOperation;

    public Client(int port, OperationsDone userOperation, Map map, GameFrame canvas, int playerTankLocX, int playerTankLocY) {
        clientPort = port;
        this.userOperation = userOperation;
        System.out.println(userOperation);
        state = new ClientState(userOperation, map.getMap(), map.getOccupierObjects(), map.getDynamicObjectsThreadPool(), playerTankLocX, playerTankLocY);
        this.canvas = canvas;
    }

    @Override
    public void run() {
        try (Socket clientSocket = new Socket("127.0.0.1", clientPort)) {
            System.out.println("Connected to server.");
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            out.writeObject(state.getPlayerTank());
            out.flush();
            Thread.sleep(2000);

            while (true) {
                Object receivedObject = in.readObject();
                if (receivedObject instanceof Boolean) {
                    break;
                }
                state.setMap((ArrayList<GameObject>) (receivedObject));
                System.out.println("RECVFromServer: " + state.getMap().size());
                state.update();
                canvas.render(state);
//            ArrayList<GameObject> map = state.getMap();
                out.writeObject(state.getMap());
                out.flush();
                //                        long delay = (1000 / 30) - (System.currentTimeMillis() - start);
                //                        if (delay > 0)
                Thread.sleep(1000);
//            System.out.println("SENT: " + state.getMap().size());

            }
            System.out.print("All messages sent.\nClosing ... ");
        } catch (IOException ex) {
            System.out.println("client");
            System.err.println(ex);
        } catch (ClassNotFoundException e) {
            System.out.println("client");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
