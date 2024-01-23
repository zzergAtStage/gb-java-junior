package chat.server.model;

import chat.server.ChatGUI.ServerControl;

import java.net.Socket;

public class ClientManager implements Runnable{
    private Socket socket;

    public ClientManager(Socket socket, ServerControl serverControl) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
