package game.gameSchematic;

import game.gameObjects.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class holds the state of game and all of its elements.
 */
public class Server implements Runnable {
    private ServerSocket server;
    //    private ArrayList<GameObject> upperLayerObjects;
    private ArrayList<GameObject> mapOccupierObjects;
    private static UserOperation userOperation;
    private static int port = 8080;
    private boolean isMultiPlayer;

    public Server(ArrayList<GameObject> mapOccupierObjects, boolean isMultiPlayer, UserOperation userOperation) {
//        this.upperLayerObjects = upperLayerObjects;
        this.mapOccupierObjects = (ArrayList<GameObject>) mapOccupierObjects.clone();
        this.isMultiPlayer = isMultiPlayer;
        this.userOperation = userOperation;
    }

    public static int getPort() {
        return port;
    }

//    public ArrayList<GameObject> getupperLayerObjects() {
//        return upperLayerObjects;
//    }
//
//    public void setupperLayerObjects(ArrayList<GameObject> upperLayerObjects) {
//        this.upperLayerObjects = upperLayerObjects;
//    }

    /**
     * it opens a ServerSocket and wait for clients to connect
     */
    @Override
    public void run() {
        try {
            int counter = 0;
            int playerCount = isMultiPlayer ? 2 : 1;
            server = new ServerSocket(port);
            while (counter < playerCount) {
                Socket client = server.accept();
                Thread clientHandler = new Thread(new ClientHandler(client));
                clientHandler.start();
                counter++;
                System.out.println(playerCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class ClientHandler implements Runnable {

        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());


                PlayerTank clientTank = (PlayerTank) in.readObject();
                mapOccupierObjects.add(clientTank);
                Thread.sleep(500);
                out.writeObject(mapOccupierObjects);
                out.flush();
                Thread.sleep(500);

                while (!userOperation.isGameOver()) {
                    System.out.println("init size: " + mapOccupierObjects.size());
                    out.writeObject(mapOccupierObjects);
                    out.flush();
                    System.out.println("SENT: " + mapOccupierObjects.size());

                    Object receivedObject = in.readObject();
//                    for (GameObject mapOccupierObject : mapOccupierObjects
//                            ) {
//                        System.out.println(mapOccupierObject);
//                    }
                    mapOccupierObjects = (ArrayList<GameObject>) receivedObject;
                    Thread.sleep(1);
                }
                System.out.print("All messages sent.\nClosing client ... ");
//                }

//                    try {
//                        long start = System.currentTimeMillis();
//
//                        //
//                        state.update();
//                        canvas.render(state);
//                        gameOver = userOperation.isGameOver();
//                        //
//                        long delay = (1000 / 30) - (System.currentTimeMillis() - start);
//                        if (delay > 0)
//                            Thread.sleep(delay);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }
}

