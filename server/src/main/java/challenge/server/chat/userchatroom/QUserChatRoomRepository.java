package challenge.server.chat.userchatroom;

import challenge.server.chat.chatroom.ChatRoom;
import challenge.server.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static challenge.server.chat.chatroom.QChatRoom.chatRoom;
import static challenge.server.chat.userchatroom.QUserChatRoom.userChatRoom;
import static challenge.server.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QUserChatRoomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    public void save(UserChatRoom userChatRoom) {
        em.persist(userChatRoom);
    }

    // 특정 유저가 참여 중인 채팅방 목록
    public List<ChatRoom> findChatRoomsByUserId(Long userId) { // 매개변수에 User 클래스가 올 수는 있지.
        return jpaQueryFactory
                .select(chatRoom)
                .from(userChatRoom)
                .where(userChatRoom.user.userId.eq(userId))
                .fetch();
    }

    // 특정 채팅방에 참여 중인 유저 목록
    public List<User> findUsersByChatRoomId(Long chatRoomId) {
        return jpaQueryFactory
                .select(user)
                .from(userChatRoom)
                .where(userChatRoom.chatRoom.chatRoomId.eq(chatRoomId))
                .fetch();
    }

}