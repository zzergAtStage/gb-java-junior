package chat.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private String userName;
    private String userLastName;
    private int userId;
    private boolean online;
}
