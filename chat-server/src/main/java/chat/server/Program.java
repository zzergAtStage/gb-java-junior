package chat.server;

import chat.server.model.ChatServer;
import chat.server.model.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Program {
    public static void main(String[] args) {
        System.out.println("Server...");

        try{
            new Server(4505).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
