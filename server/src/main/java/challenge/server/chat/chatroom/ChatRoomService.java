package challenge.server.chat.chatroom;

import challenge.server.chat.chatMessage.ChatMessage;
import challenge.server.chat.userchatroom.UserChatRoomService;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
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
    private final ChatRoomMapper mapper;


    // 1. 채팅방 생성 - mapper 변환, repo(ChatRoom, UserChatRoom) 저장
    @Transactional
    public ChatRoomDto.ResponseDetail createChatRoom(ChatRoomDto.Request chatRoomDto) {
        // 1. ChatRoom Entity 변환 & Repo 저장
        ChatRoom chatRoom = chatRoomRepository.save(mapper.dtoToChatRoom(chatRoomDto)); // (1) chatRoom에 userChatRooms, ChatMessages 없음

        // 2. UserChatRoom repo 저장 - List<Long> participants 순회 & User, ChatRoom 엔티티 저장.
        userChatRoomService.saveUserChatRooms(chatRoomDto, chatRoom);

        ChatRoomDto.ResponseDetail savedChatRoom = mapper.chatRoomToDtoDetail(chatRoom);

        List<User> users = userChatRoomService.findUsersByChatRoomId(chatRoom.getChatRoomId());
        savedChatRoom.setParticipants(userService.usersToDtos(users));
        // (2) 아직 userChatRoom들이 commit되지 않아서 chatRoom에 userChatRoom들이 없는 상태이지 않을까? 근데 chatroom에서 userChatroom으로 접근 안해서 괜찮을듯?

        return savedChatRoom;
    }

    // 2. chatRoomId로 채팅방 조회
    public ChatRoomDto.ResponseDetail findChatRoom(Long chatRoomId) {
        // 1. chatRoomId로 ChatRoom 리턴
        ChatRoomDto.ResponseDetail findChatRoom = mapper.chatRoomToDtoDetail(findByChatRoomId(chatRoomId));
        // 2. chatRoomId로 Users 리스트 리턴
        List<User> users = userChatRoomService.findUsersByChatRoomId(chatRoomId);
        // 3. ResponseDetail에 UserDto 저장
        findChatRoom.setParticipants(userService.usersToDtos(users));

        return findChatRoom;
    }

    public ChatRoom findByChatRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.CHATROOM_NOT_FOUND));
    }

    // 3. 채팅방 목록 조회(마지막 채팅들 조회) -> ChatService로 부터 호출 됨.
    public List<ChatRoomDto.ResponseSimple> chatMessagesToDtoSimples(List<ChatMessage> chatMessages) {
        return mapper.chatMessagesToDtoSimples(chatMessages);
    }
    // 채팅방 생성 -> repo.save
    // 채팅방 초대 -> room.changeUser
    // 채팅방 나가기 -> room.changeUser

}