package chat.client;

import chat.client.model.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Program {

    public static void main(String[] args) {
        System.out.println("Client..." );
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket socket = new Socket(address, 4500);
            Client client = new Client(socket, "Client");
            String remoteIp = address.getHostAddress();
            System.out.println("Remote ip = " + remoteIp);
            System.out.println("Socket = " + socket.getLocalPort());

            String clientName = client.getName();
            System.out.println("Client name = " + clientName);

            client.listenForMessages();
            client.sendMessage(new Message());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
