package challenge.server.chat.chatMessage;

import challenge.server.audit.BaseTimeEntity;
import challenge.server.chat.chatroom.ChatRoom;
import challenge.server.user.entity.User;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    public enum Status {
        ENTER(1), MESSAGE(2), LEAVE(3);

        @Getter
        private final int type;

        Status(int type) {
            this.type = type;
        }
    }

    public void changeSender(User user) {
        this.sender = user;
    }

    public void changeChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}