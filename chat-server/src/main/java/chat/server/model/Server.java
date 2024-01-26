package chat.server.model;

import chat.server.ChatGUI.ServerControl;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final ServerSocket serverSocket;
    public Server(ServerSocket socket) {
        this.serverSocket = socket;
    }
    public void runServer(){
        //up the window. May be use invokeLater?
        ServerControl serverWindows = new ServerControl();
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Подключен новый клиент..." + serverSocket.getLocalSocketAddress());
                serverWindows.logStatus("Подключен новый клиент..." + socket.getLocalPort() + ":" + socket.getLocalSocketAddress());
                ClientManager clientManager = new ClientManager(socket, serverWindows);
                Thread thread = new Thread(clientManager);
                thread.start();
            }
        } catch (IOException e) {
            closeSocket();
            serverWindows.dispose();
        }
    }

    private void closeSocket() {
        try{
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
