package chat.server.model;

import chat.server.ChatGUI.ServerControl;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private boolean isServerWorking;

    private final ServerControl listener;

    public ChatServer(ServerControl listener) {
        this.listener = listener;
        this.isServerWorking = false;
    }

    public void start(){
        if (isServerWorking){
            listener.onMessageReceived("server is already working.");
            return;
        }
        listener.onMessageReceived("Server started.");
        isServerWorking = true;
    }
    public void stop(){
        if (!isServerWorking){
            listener.onMessageReceived("server is already stopped.");
            return;
        }
        listener.onMessageReceived("Server stopped.");
        isServerWorking = false;
    }

}
