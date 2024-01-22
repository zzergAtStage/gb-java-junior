package chat.server.model;

public class ServerSocketListener {
    private ServerSocketThreadListener listener;

    public ServerSocketListener(ServerSocketThreadListener listener) {
        this.listener = listener;
    }
}
