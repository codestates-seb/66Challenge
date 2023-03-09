package challenge.server.domain.chat.chatMessage;

import challenge.server.domain.chat.chatroom.ChatRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge.server.domain.chat.chatMessage.QChatMessage.chatMessage;


@Repository
@RequiredArgsConstructor
public class QChatMessageRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ChatMessage> findAllChatsByChatRoomId(Long chatRoomId, Long lastChatMessageId, int size) {
        return jpaQueryFactory.selectFrom(chatMessage)
                .where(chatMessage.chatRoom.chatRoomId.eq(chatRoomId),
                        ltChatMessageId(lastChatMessageId))
                .orderBy(chatMessage.chatMessageId.desc())
                .limit(size)
                .fetch();
    }

    public List<ChatMessage> findLastChatsByUserId(List<ChatRoom> chatRooms){
        return jpaQueryFactory
                .selectFrom(chatMessage) // chatMessage 테이블에서 셀렉한다.
                .where(Expressions.list(chatMessage.chatRoom, chatMessage.createdAt) // 챗방, 생성일시가 다음 리스트 안에 포함되는 채팅에 한하여
                        .in(JPAExpressions
                                .select(chatMessage.chatRoom, chatMessage.createdAt.max()) // 챗방, 생성일시(가장 큰 챗)를 셀렉한다.
                                .from(chatMessage) // 채팅 테이블에서
                                .where(chatMessage.chatRoom.in(chatRooms)) // 챗방이 chatRooms 리스트 안에 포함되는 채팅에 한하여
                                .groupBy(chatMessage.chatRoom))) // 챗방으로 묶는다
                .orderBy(chatMessage.createdAt.desc()) // 생성일시를 기준으로 내림차순한다
                .fetch();
    }

    // TODO 채팅방 목록에 no-offset 적용해보기, 의문: Id가 띄엄띄엄있어도 페이지네이션 정상 동작?
    public List<ChatMessage> findAllLastChats() {
        return jpaQueryFactory
                .selectFrom(chatMessage)
                .where(Expressions.list(chatMessage.chatRoom, chatMessage.createdAt)
                        .in(JPAExpressions
                                .select(chatMessage.chatRoom, chatMessage.createdAt.max())
                                .from(chatMessage)
                                .groupBy(chatMessage.chatRoom)))
                .orderBy(chatMessage.createdAt.desc())
                .fetch();
    }

    private BooleanExpression ltChatMessageId(Long lastChatMessageId) {
        return lastChatMessageId == null ? null : chatMessage.chatMessageId.lt(lastChatMessageId);
    }
}
