package chat.client.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Chat {
    private Collection<User> activeUsers;
    //as the key, I will keep userId - to realize feature - U2U conversation
    private List<Message> messages;
    private User currentUser;

    public Chat(){
        this.messages = new ArrayList<>();
        this.activeUsers = new ArrayList<>();
    }

    public void addMessage(Message message){
        messages.add(message);
    }
    public List<Message> getAllMessages(){
        return messages;
    }

    public List<Message> getMessagesByUser(String userId){
        return messages
                .stream()
                .filter(message -> message.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void addUserToChat(User user){
        this.activeUsers.add(user);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void restoreChat(List<Message> messageList){
        this.messages = messageList;
    }
}
