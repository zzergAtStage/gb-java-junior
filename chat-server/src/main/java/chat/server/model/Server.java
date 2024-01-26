package chat.server.model;

import chat.server.ChatGUI.ServerControl;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private  int port = 4500;
    private ServerSocket serverSocket;
    private List<User> clients;
    private ServerControl serverWindow;
    public Server(int port){
        this.port = port;
        this.clients = new ArrayList<>();
    }
    public Server(ServerSocket socket) {
        this.serverSocket = socket;
        this.clients = new ArrayList<>();
    }

    public void run() throws IOException {
        serverSocket = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("Port " + this.port + " is now open.");
        serverWindow = new ServerControl();
        while (true) {
            // accepts a new client
            Socket client = serverSocket.accept();

            // get nickname of newUser
            String nickname = (new Scanner( client.getInputStream() )).nextLine();
            nickname = nickname.replace(",", ""); //  ',' use for serialisation
            nickname = nickname.replace(" ", "_");
            StringBuilder serverMessage = new StringBuilder("New Client: \"" + nickname + "\"\n\t     Host:" + client.getInetAddress().getHostAddress());
            System.out.println(serverMessage);
            serverWindow.logStatus(serverMessage.toString());
            // create new User
            User newUser = new User(client, nickname);

            // add newUser message to list
            this.clients.add(newUser);

            // Welcome msg
            newUser.getOutStream().println(
                    "<img src='https://www.kizoa.fr/img/e8nZC.gif' height='42' width='42'>"
                            + "<b>Welcome</b> " + newUser.toString() +
                            "<img src='https://www.kizoa.fr/img/e8nZC.gif' height='42' width='42'>"
            );

            // create a new thread for newUser incoming messages handling
            new Thread(new UserHandler(this, newUser)).start();
        }
    }

    private void closeSocket() {
        try{
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            serverWindow.dispose();
        }
    }
    // delete a user from the list
    public void removeUser(User user){
        this.clients.remove(user);
    }

    // send incoming msg to all Users
    public void broadcastMessages(String msg, User userSender) {
        for (User client : this.clients) {
            client.getOutStream().println(
                    userSender.toString() + "<span>: " + msg+"</span>");
        }
    }
    public void broadcastAllUsers(){
        for (User client : this.clients) {
            client.getOutStream().println(this.clients);
        }
    }

    // send message to a User (String)
    public void sendMessageToUser(String msg, User userSender, String user){
        boolean find = false;
        for (User client : this.clients) {
            if (client.getNickname().equals(user) && client != userSender) {
                find = true;
                userSender.getOutStream().println(userSender.toString() + " -> " + client.toString() +": " + msg);
                client.getOutStream().println(
                        "(<b>Private</b>)" + userSender.toString() + "<span>: " + msg+"</span>");
            }
        }
        if (!find) {
            userSender.getOutStream().println(userSender.toString() + " -> (<b>no one!</b>): " + msg);
        }
    }

}
