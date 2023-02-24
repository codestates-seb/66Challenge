package challenge.server.chat.userchatroom;

import challenge.server.chat.chatroom.ChatRoom;
import challenge.server.chat.chatroom.ChatRoomDto;
import challenge.server.chat.chatroom.ChatRoomService;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
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
}
