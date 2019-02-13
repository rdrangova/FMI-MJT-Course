package bg.sofia.uni.fmi.mjt.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClientConnectionRunnable implements Runnable {

    private String username;
    private Socket socket;

    public ClientConnectionRunnable(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String commandInput = reader.readLine();

                if (commandInput != null) {
                    String[] tokens = commandInput.split("\\s+");
                    String command = tokens[0];
                    if ("send".equals(command)) {
                        String to = tokens[1];
                        String message = commandInput.substring(command.length() + to.length() + 1);

                        Socket toSocket = ChatServer.getUser(to);
                        if (toSocket == null) {
                            writer.println(String.format("=> %s seems to be offline", to));
                            continue;
                        }
                        System.out.println("inside");
                        send(toSocket, message, writer, to);
                        System.out.println("successfully send message");
                    } else if ("list-users".equals(command)) {
                        Set<String> usernames = ChatServer.getUsernames();
                        if (usernames.isEmpty()) {
                            writer.println("there is nobody online");
                            continue;
                        }
                        for (String s : usernames) {
                            writer.println(s);
                        }
                        System.out.println("successfully listed " + usernames.size() + " users");
                    } else if ("send-all".equals(command)) {
                        Set<String> usernames = ChatServer.getUsernames();
                        if (usernames.size() == 1) {
                            writer.println("there is nobody else online");
                            continue;
                        }
                        String message = commandInput.substring(command.length());
                        for (String s : usernames) {
                            if (!s.equals(username)) {
                                send(ChatServer.getUser(s), message, writer, s);
                            }
                        }
                        System.out.println("successfully sent message to " + (usernames.size() - 1) + " users");
                    } else if ("disconnect".equals(command)) {
                        ChatServer.removeUser(username);
                        socket.close();
                        System.out.println(String.format("%s disconnected", username));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("socket is closed");
            System.out.println(e.getMessage());
        }
    }

    private void send(Socket toSocket, String message, PrintWriter writer, String to) {
        PrintWriter toWriter = null;
        try {
            toWriter = new PrintWriter(toSocket.getOutputStream(), true);
        } catch (IOException e) {
            writer.println(String.format("=> %s seems to be offline", to));
        }
        toWriter.println(String.format("[%s]: %s", username, message));
    }

}
