package chat.server.model;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener{
    private boolean isServerWorking;
    private ServerSocketListener server;
    private final ChatServerListener listener;

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
        this.isServerWorking = false;
    }

    public void start(){
        if (isServerWorking){
            listener.onMessageReceived("server is already working.");
            return;
        }
        server = new ServerSocketListener(this);
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

    @Override
    public void onServerStart() {
        listener.onMessageReceived("Server thread started.");
    }

    @Override
    public void onServerStop() {
        listener.onMessageReceived("Server thread stopped");
    }

    @Override
    public void onServerSocketCreated(ServerSocket socket) {
        listener.onMessageReceived("Server Socked Created");
    }

    @Override
    public void onServerSocketTimeout(ServerSocket socket) {

    }

    @Override
    public void onSocketAccepted(ServerSocket s, Socket client) {
        listener.onMessageReceived("client connected");
    }

    @Override
    public void onServerException(Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public void onSocketStart(Socket s) {
        listener.onMessageReceived("Client connected as client");
    }

    @Override
    public void onSocketStop() {
        listener.onMessageReceived("Client dropped");
    }

    @Override
    public void onSocketReady(Socket socket) {
        listener.onMessageReceived("Client is ready");
    }

    @Override
    public synchronized void onReceivedString(Socket s, String message) {
        listener.onMessageReceived(message);
    }
}
