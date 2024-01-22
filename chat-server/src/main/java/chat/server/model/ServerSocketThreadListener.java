package chat.server.model;

import java.net.ServerSocket;
import java.net.Socket;

public interface ServerSocketThreadListener {
    void onServerStart();
    void onServerStop();
    void onServerSocketCreated(ServerSocket socket);
    void onServerSocketTimeout(ServerSocket socket);
    void onSocketAccepted(ServerSocket s, Socket client);
    void onServerException(Throwable exception);
}
