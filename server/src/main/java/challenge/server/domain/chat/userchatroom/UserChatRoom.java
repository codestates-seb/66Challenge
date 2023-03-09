package challenge.server.domain.chat.userchatroom;

import challenge.server.domain.BaseTimeEntity;
import challenge.server.domain.chat.chatroom.ChatRoom;
import challenge.server.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userChatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CHAT_ROOM_ID")
    private ChatRoom chatRoom;
}
