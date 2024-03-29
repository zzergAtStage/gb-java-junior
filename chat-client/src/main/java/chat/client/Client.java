package chat.client;

import chat.client.chatGUI.ChatWindow;
import chat.client.model.Message;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    @Getter
    private final String name;



    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;



    public Client(Socket socket, String name) {

        this.socket = socket;
        this.name = name ;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            // Завершаем работу буфера на чтение данных
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            // Завершаем работу буфера для записи данных
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            // Завершаем работу клиентского сокета
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listenForMessages(ChatWindow jchat){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected()){
                    try {
                        while ((message = bufferedReader.readLine()) != null){
                            System.out.println(message);
                            jchat.putMessage(message);
                        }
                    }
                    catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }

        }).start();
    }

    public void sendMessage(Message message){

        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            //TODO bind with control window
            bufferedWriter.write(name + ": " + message.getMessageBody());
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }
        catch (IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    public static Client create(Socket socket){
        return new Client(socket, "Client");
    }
}
