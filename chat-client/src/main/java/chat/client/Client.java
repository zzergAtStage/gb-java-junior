package chat.client;

import java.net.Socket;

public class Client {
    private static int counter;
    private final Socket socket;
    private final String name;

    public Client(Socket socket, String name) {
        counter++;
        this.socket = socket;
        this.name = "Client_" + counter;
    }

    public void listenForMessages(){

    }
    
}
