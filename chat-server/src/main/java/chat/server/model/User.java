package chat.server.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class User {
    private static int nbUser = 0;
    private int userId;
    private PrintStream streamOut;
    private InputStream streamIn;
    private String nickname;
    private Socket client;

    // constructor
    public User(Socket client, String name) throws IOException {
        this.streamOut = new PrintStream(client.getOutputStream());
        this.streamIn = client.getInputStream();
        this.client = client;
        this.nickname = name;
        this.userId = nbUser;
        nbUser += 1;
    }
    public PrintStream getOutStream(){
        return this.streamOut;
    }
    public InputStream getInputStream(){
        return this.streamIn;
    }

    public String getNickname(){
        return this.nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}