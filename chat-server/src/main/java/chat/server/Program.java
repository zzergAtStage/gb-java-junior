package chat.server;

import chat.server.model.ChatServer;
import chat.server.model.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Program {
    public static void main(String[] args) {
        System.out.println("Server...");

        try{
            ServerSocket socket = new ServerSocket(4500);
            //TODO> ChatServer server = new ChatServer()
            Server server = new Server(socket);
        server.runServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
