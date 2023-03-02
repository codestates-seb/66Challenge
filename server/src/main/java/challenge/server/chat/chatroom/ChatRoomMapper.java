package challenge.server.chat.chatroom;

import challenge.server.chat.chatMessage.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatRoomMapper {

    // request DTO -> ChatRoom entity / title 만 매핑하면 됨.
    public ChatRoom dtoToChatRoom(Long habitId, String title){
        return ChatRoom.builder()
                .title(title + "오픈채팅방")
                .habitId(habitId)
                .build();
    }

    // Chatroom -> responseDetail DTO
    public ChatRoomDto.ResponseDetail chatRoomToDtoDetail(ChatRoom chatRoom){
        return ChatRoomDto.ResponseDetail.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .title(chatRoom.getTitle())
                // TODO participants ChatRoomService에서 UserService를 통해서 주입받기
                .build();
    }

    // Chatroom -> responseSimple DTO
    public ChatRoomDto.ResponseSimple chatMessageToDtoSimple(ChatMessage chatMessage){
        return ChatRoomDto.ResponseSimple.builder()
                .chatRoomId(chatMessage.getChatRoom().getChatRoomId())
                .title(chatMessage.getChatRoom().getTitle())
                .lastChat(chatMessage.getContent())
                .lastChatAt(chatMessage.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM")))

                .build();
    }

    public List<ChatRoomDto.ResponseSimple> chatMessagesToDtoSimples(List<ChatMessage> chatMessages) {
        return chatMessages.stream().map(
                chatMessage -> chatMessageToDtoSimple(chatMessage)
        ).collect(Collectors.toList());
    }
}
