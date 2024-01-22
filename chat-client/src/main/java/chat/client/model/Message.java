package chat.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String userId;
    private String toUserId;
    private String messageSendTime;
    private String messageBody;
}
