package challenge.server.chat.chatMessage;

import challenge.server.chat.chatroom.ChatRoom;
import challenge.server.chat.chatroom.ChatRoomDto;
import challenge.server.chat.chatroom.ChatRoomService;
import challenge.server.chat.userchatroom.UserChatRoomService;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final UserChatRoomService userChatRoomService;
    private final QChatMessageRepository qChatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Transactional
    public ChatMessageDto.Response saveMessage(ChatMessageDto.Request message) {

        User sender = userService.findUser(message.getUserId());
        ChatRoom chatRoom = chatRoomService.findByChatRoomId(message.getChatRoomId());

        String status = message.getStatus();
        String nickname = sender.getUsername();

        switch (status) {
            case "ENTER":
                message.setContent(nickname+"님이 입장하셨습니다.");
                break;
            case "LEAVE":
                message.setContent(nickname+"님이 나가셨습니다.");
                break;
            default:
        }

        ChatMessage chatMessage = chatMessageMapper.dtoToChatMessage(message);
        chatMessage.changeSender(sender);
        chatMessage.changeChatRoom(chatRoom);

        ChatMessageDto.Response response = chatMessageMapper.chatMessageToDto(chatMessage);
        response.setNickname(nickname);

        return response;
    }

    // 2. 특정 채팅방의 모든 채팅을 리턴
    @Transactional
    public List<ChatMessageDto.Response> findAllChatsByChatRoomId(Long chatRoomId, Long lastChatMessageId, int size) {
        // chatMessage 들을 찾고, mapper를 통해 바꿔주고 리턴
        return chatMessageMapper.chatMessagesToDtos(findAllByChatRoomId(chatRoomId,lastChatMessageId,size));
    }

    public List<ChatMessage> findAllByChatRoomId(Long chatRoomId, Long lastChatMessageId, int size) {
        return qChatMessageRepository.findAllChatsByChatRoomId(chatRoomId, lastChatMessageId, size);
    }


    // 3. 채팅방 목록 조회(전체 채팅방의 마지막 채팅을 리턴)
    public List<ChatRoomDto.ResponseSimple> findAllChatRooms() {
        List<ChatRoomDto.ResponseSimple> response = chatRoomService.chatMessagesToDtoSimples(findAllLastChats());

        // 각 ChatRoomDto.Simple에 participants를 userChatRoomService를 통해 List<User>를 받아 DTO로 변환해 set한다.
        response.stream().forEach(
                chatRoomDtoSimple -> chatRoomDtoSimple.setParticipants(userService.usersToRoomDtos(
                        userChatRoomService.findUsersByChatRoomId(chatRoomDtoSimple.getChatRoomId())
                ))
        );
        return response;
    }

    public List<ChatMessage> findAllLastChats() {
        return qChatMessageRepository.findAllLastChats();
    }

    // 4. 채팅방 목록 조회 - 특정 유저가 참여 중인
    public List<ChatRoomDto.ResponseSimple> findChatRoomsByUserId (Long userId){
        // 특정 유저가 참여 중인 채팅방 목록
        List<ChatRoom> chatRooms = userChatRoomService.findChatRoomsByUserId(userId);

        // 채팅방 목록을 기준으로 마지막 채팅들을 select -> ChatRoomDto들로 변환 -> 각 Dto의 chatRoomId로 participants 구하여 set
        List<ChatRoomDto.ResponseSimple> response = chatRoomService.chatMessagesToDtoSimples(findLastChatsByUserId(chatRooms));
        response.stream().forEach(
                chatRoomDtoSimple -> chatRoomDtoSimple.setParticipants(userService.usersToRoomDtos(
                        userChatRoomService.findUsersByChatRoomId(chatRoomDtoSimple.getChatRoomId())
                ))
        );

        return response;
    }

    public List<ChatMessage> findLastChatsByUserId(List<ChatRoom> chatRooms) {
        return qChatMessageRepository.findLastChatsByUserId(chatRooms);
    }

    // TODO 채팅 목록에서 특정 키워드로 채팅 찾기
    // 전체 채팅 리스트와 인덱스 리스트를 함께 주면 될 듯.
    // 인덱스리스트를 찾는 메서드 필요, 응답으로 묶어줄 객체 필요
    // public List<ChatMessageDto.Response> findChatsBykeyword(Long chatRoomId,)

}