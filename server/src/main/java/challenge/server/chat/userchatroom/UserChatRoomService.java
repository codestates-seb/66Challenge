package challenge.server.chat.userchatroom;

import challenge.server.chat.chatroom.ChatRoom;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChatRoomService {

    private final QUserChatRoomRepository qUserChatRoomRepository;

    // 특정 유저가 참여 중인 채팅방 목록
    public List<ChatRoom> findChatRoomsByUserId(Long userId) {
        return qUserChatRoomRepository.findChatRoomsByUserId(userId);
    }

    // 특정 채팅방에 참여 중인 유저 목록
    public List<User> findUsersByChatRoomId(Long chatRoomId) {
        return qUserChatRoomRepository.findUsersByChatRoomId(chatRoomId);
    }

    // 특정 채팅방 + 특정 유저로
    public UserChatRoom findByChatRoomIdAndUserId(Long chatRoomId, Long userId) {
        UserChatRoom userChatRoom = qUserChatRoomRepository.findByChatRoomIdAndUserId(chatRoomId, userId);
        if(userChatRoom == null) throw new BusinessLogicException(ExceptionCode.USER_NOT_PARTICIPATE);
        return userChatRoom;
    }

    // 특정 채팅방 + 특정 유저로 삭제
    @Transactional
    public void delete(Long chatRoomId, Long userId) {
        qUserChatRoomRepository.delete(findByChatRoomIdAndUserId(chatRoomId, userId));
    }
}
