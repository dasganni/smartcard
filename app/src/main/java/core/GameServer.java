package core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    public  static final int PORT = 4444;

    private List<Socket> clients;
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public GameServer() {
        clients = new LinkedList<>();
        executorService = Executors.newFixedThreadPool(2);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startLoby() {
        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                try {
                    clients.add(serverSocket.accept());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void gameInit() {
        clients.forEach(c -> {
            executorService.submit(() -> {
                try (PrintWriter out = new PrintWriter(c.getOutputStream(), true )) {
                    out.print("start");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

}
