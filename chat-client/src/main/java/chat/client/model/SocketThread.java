package chat.client.model;

public class SocketThread {
    private final SocketThreadListener listener;

    public SocketThread(SocketThreadListener listener) {
        this.listener = listener;
    }
}
