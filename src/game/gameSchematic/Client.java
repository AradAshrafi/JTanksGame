package game.gameSchematic;

import game.FileOperation.Map;
import game.gameObjects.GameObject;
import game.gameObjects.Wall;
import game.gameSchematic.betweenObjectRelation.OperationsDone;
import javafx.scene.Camera;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/**
 * each client will be one runnable object
 *
 * @author Arad
 */
public class Client implements Runnable {
    private int clientPort;
    private static ClientState state;
    private static GameFrame canvas;
    private static OperationsDone userOperation;

    public Client(int port, OperationsDone userOperation, Map map, GameFrame canvas, int playerTankLocX, int playerTankLocY) {
        clientPort = port;
        this.userOperation = userOperation;
        state = new ClientState(userOperation, map.getUnderLayerObjects(), map.getOccupierObjects(), map.getDynamicObjectsThreadPool(), playerTankLocX, playerTankLocY);
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
            Thread.sleep(500);
            state.setMapOccupierObjects((ArrayList<GameObject>) in.readObject());
//            Thread.sleep(500);

            while (true) {
                Object receivedObject = in.readObject();
                if (receivedObject instanceof Boolean) {
                    break;
                }

                ArrayList<GameObject> mapOccupiersFromServer = ((ArrayList<GameObject>) (receivedObject));
                for (GameObject mapOccupierFromServer : mapOccupiersFromServer) {
                    if (mapOccupierFromServer instanceof Wall)
                        continue;
                    mapOccupierFromServer.setObjectImage();
                    mapOccupierFromServer.setRelativeLocX(mapOccupierFromServer.getLocX() -
                            ClientState.getCamera().getCameraWestBorder());
                    mapOccupierFromServer.setRelativeLocY(mapOccupierFromServer.getLocY() - ClientState.getCamera().getCameraNorthBorder());

                }

                state.setMapOccupierObjects(mapOccupiersFromServer);
                System.out.println("RECVFromServer: " + state.getMapOccupierObjects().size());
                state.update();
                canvas.render(state);
//            ArrayList<GameObject> map = state.getMap();
                out.writeObject(state.getMapOccupierObjects());
                out.flush();
                //                        long delay = (1000 / 30) - (System.currentTimeMillis() - start);
                //                        if (delay > 0)
                Thread.sleep(1);

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
