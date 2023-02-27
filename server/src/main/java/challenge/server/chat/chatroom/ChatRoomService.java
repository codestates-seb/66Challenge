package challenge.server.chat.chatroom;

import challenge.server.chat.chatMessage.ChatMessage;
import challenge.server.chat.userchatroom.QUserChatRoomRepository;
import challenge.server.chat.userchatroom.UserChatRoom;
import challenge.server.chat.userchatroom.UserChatRoomService;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final UserService userService;
    private final UserChatRoomService userChatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    private final QUserChatRoomRepository qUserChatRoomRepository;
    private final ChatRoomMapper mapper;

    // 1. 채팅방 생성 - mapper 변환, repo(ChatRoom, UserChatRoom) 저장
    @Transactional
    public ChatRoomDto.ResponseDetail createChatRoom(Long habitId, String title) {
        // ChatRoom Entity 변환 & Repo 저장
        ChatRoom chatRoom = chatRoomRepository.save(mapper.dtoToChatRoom(habitId, title));
        return mapper.chatRoomToDtoDetail(chatRoom);
    }

    // 2. chatRoomId로 채팅방 조회
    public ChatRoomDto.ResponseDetail findChatRoom(Long chatRoomId) {
        return returnChatRoomResponse(findByChatRoomId(chatRoomId));
    }

    public ChatRoom findByChatRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.CHATROOM_NOT_FOUND));
    }

    // 3. 채팅방 목록 조회(마지막 채팅들 조회) -> ChatService로 부터 호출 됨.
    public List<ChatRoomDto.ResponseSimple> chatMessagesToDtoSimples(List<ChatMessage> chatMessages) {
        return mapper.chatMessagesToDtoSimples(chatMessages);
    }

    // 4. habitId로 채팅방 조회
    public ChatRoom findByHabitId(Long habitId) {
        return chatRoomRepository.findByHabitId(habitId).orElseThrow(
                ()-> new BusinessLogicException(ExceptionCode.CHATROOM_NOT_FOUND)
        );
    }

    // 5. 특정 유저가 특정 채팅방에 참여
    @Transactional
    public ChatRoomDto.ResponseDetail enterChatRoom(Long userId, Long habitId) {

        ChatRoom chatRoom = findByHabitId(habitId);

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(userService.findUser(userId))
                .chatRoom(chatRoom)
                .build();
        qUserChatRoomRepository.save(userChatRoom);

        return returnChatRoomResponse(chatRoom);
    }

    // 6. 특정 유저가 채팅방에서 나감
    @Transactional
    public void leaveChatRoom(Long chatRoomId, Long userId) {
        // 1. UserChatRoom Repository에서 행 제거
        userChatRoomService.delete(chatRoomId, userId);
        // deleteIfNoUsers(chatRoomId);
        // 2. 전체 채팅방 목록(마지막 채팅) 리턴 -> Controller
    }

    @Transactional
    public void deleteIfNoUsers(Long chatRoomId) {
        if(userChatRoomService.findUsersByChatRoomId(chatRoomId).size()==0) {
            deleteChatRoom(chatRoomId);
        }
    }

    // 채팅방 삭제 메서드
    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.delete(findByChatRoomId(chatRoomId));
    }

    // ChatRoomResponseDto로 전환하는 메서드
    public ChatRoomDto.ResponseDetail returnChatRoomResponse(ChatRoom chatRoom) {
        // 1. chatRoom -> ChatRoomDTO 리턴
        ChatRoomDto.ResponseDetail chatRoomDto = mapper.chatRoomToDtoDetail(chatRoom);
        // 2. chatRoomId로 Users 리스트 리턴
        List<User> users = userChatRoomService.findUsersByChatRoomId(chatRoom.getChatRoomId());
        // 3. ResponseDetail에 UserDto 저장
        chatRoomDto.setParticipants(userService.usersToChatUsers(users));

        return chatRoomDto;
    }
}