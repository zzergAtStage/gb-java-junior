package chat.client.services;

import chat.client.model.ChatTransport;
import chat.client.model.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ChatFileTransport implements ChatTransport {
    private FileWriter fileWriter;


    @Override
    public void saveMessage(List<Message> message) {
        //In this class every message will be added to existing file-log
        try {
            fileWriter = new FileWriter("ChatLog.txt");
            BufferedWriter file = new BufferedWriter(fileWriter);
            System.out.println(message.size());
            Gson gson = new Gson();
            gson.toJson(message, file);
            file.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Message> readChatHistory() {
        try {
            File file = new File("ChatLog.txt");
            if (!file.exists()) {
                System.out.println("There is no " + file.getPath());
                return null;
            }
            try (FileReader fileReader = new FileReader(file)) {
                return parseFile(fileReader);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Message> parseFile(FileReader fileReader) {
        try {
            Gson gson = new Gson();
            List<Message> list;
            Type messageTypeList = new TypeToken<List<Message>>() {
            }.getType();
            list = gson.fromJson(fileReader, messageTypeList);
            return list;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }

    public void closeResource() {
        try {
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
