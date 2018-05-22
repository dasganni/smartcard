package core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    Socket socket;

    public GameClient(String ip) {
        try {
            socket = new Socket(ip, GameServer.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String s) {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true )) {
            out.print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try (Scanner in = new Scanner(socket.getInputStream())) {
            return in.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
