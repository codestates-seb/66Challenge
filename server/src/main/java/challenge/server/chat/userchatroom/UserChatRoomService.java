package challenge.server.chat.userchatroom;

import challenge.server.chat.chatroom.ChatRoom;
import challenge.server.chat.chatroom.ChatRoomDto;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChatRoomService {

    private final QUserChatRoomRepository userChatRoomRepository;
    private final UserService userService;

    // 채팅방 생성 시 채팅방과 참여하는 유저들 정보를 저장
    @Transactional
    public void saveUserChatRooms(ChatRoomDto.Request chatRoomDto, ChatRoom chatRoom) {
        chatRoomDto.getParticipants().stream().forEach(
                userId -> userChatRoomRepository.save(
                        UserChatRoom.builder()
                                .user(userService.findUser(userId))
                                .chatRoom(chatRoom) // (1) ? chatRoom이 아직 commit되지 않아도 괜찮은 것인가?
                                .build()
                )
        );
    }

    // 특정 유저가 참여 중인 채팅방 목록
    public List<ChatRoom> findChatRoomsByUserId(Long userId) {
        return userChatRoomRepository.findChatRoomsByUserId(userId);
    }

    // 특정 채팅방에 참여 중인 유저 목록
    public List<User> findUsersByChatRoomId(Long chatRoomId) {
        return userChatRoomRepository.findUsersByChatRoomId(chatRoomId);
    }
}
