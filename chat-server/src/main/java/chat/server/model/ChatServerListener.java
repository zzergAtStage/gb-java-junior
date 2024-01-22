package chat.server.model;

public interface ChatServerListener {
    void onMessageReceived(String message);
}
