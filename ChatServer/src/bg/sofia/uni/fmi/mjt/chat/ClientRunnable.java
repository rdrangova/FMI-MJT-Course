package bg.sofia.uni.fmi.mjt.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRunnable implements Runnable {

    private Socket socket;

    public ClientRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                if (socket.isClosed()) {
                    System.out.println("client socket is closed, stop waiting for server messages");
                    return;
                }

                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}