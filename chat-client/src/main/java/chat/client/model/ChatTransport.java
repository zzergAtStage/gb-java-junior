package chat.client.model;

import java.util.List;

public interface ChatTransport {
    void saveMessage(List<Message> message);
    List<Message> readChatHistory();

    void closeResource();
}
