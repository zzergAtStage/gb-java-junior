package chat.client;

import chat.client.chatGUI.ChatWindow;
import chat.client.model.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Program {

    public static void main(String[] args) {
        new ChatWindow();

    }
}
