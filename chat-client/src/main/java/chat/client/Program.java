package chat.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Program {

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Client...");
        try {
            InetAddress address = InetAddress.getLocalHost();

            Socket socket = new Socket(address, 5000);
            Client client = new Client(socket, "");
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
