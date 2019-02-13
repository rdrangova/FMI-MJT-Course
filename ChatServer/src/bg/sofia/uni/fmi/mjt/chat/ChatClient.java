package bg.sofia.uni.fmi.mjt.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) throws IOException {
        new ChatClient().run();
    }

    public void run() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();

                String[] tokens = input.split(" ");
                String command = tokens[0];

                if ("connect".equals(command)) {
                    String host = tokens[1];
                    int port = Integer.parseInt(tokens[2]);
                    String username = tokens[3];

                    connect(host, port, username);
                } else {
                    if (command.equals("disconnect")) {
                        writer.println("disconnect");
                        writer.close();
                        break;
                    }
                    writer.println(input);
                    // a server command is received
                }
            }
        }
    }

    private void connect(String host, int port, String username) {
        try {
            Socket socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("successfully opened a socket");
            writer.println(username);

            ClientRunnable clientRunnable = new ClientRunnable(socket);
            new Thread(clientRunnable).start();
        } catch (IOException e) {
            System.out.println("=> cannot connect to server on localhost:8080, make sure that the server is started");
        }
    }
}