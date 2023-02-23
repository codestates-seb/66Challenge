package challenge.server.chat.chatMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMessageMapper {

    public ChatMessage dtoToChatMessage(ChatMessageDto.Request dto) {
        return ChatMessage.builder()
                .chatMessageId(dto.getChatRoomId())
                .status(ChatMessage.Status.valueOf(dto.getStatus()))
                .content(dto.getContent())
//                .sender(userService.findByUserId(dto.getUserId()))
//                // mapper안에서 Service를 의존하기 보다, mapper를 호출하는 Service에서 타 Service를 호출할 일이 또 있으면 밖에서 의존하기
//                .chatRoom(chatRoomService.findByChatRoomId(dto.getChatRoomId()))
                .build();
    }

    public ChatMessageDto.Response chatMessageToDto(ChatMessage chatMessage) {
        return ChatMessageDto.Response.builder()
                .chatRoomId(chatMessage.getChatRoom().getChatRoomId())
                .userId(chatMessage.getSender().getUserId())
                .nickname(chatMessage.getSender().getUsername())
                .profileImg(chatMessage.getSender().getProfileImageUrl())
                .status(chatMessage.getStatus().toString()) // enum -> string
                .content(chatMessage.getContent())
                // TODO 시간 이쁘게 받기
                .createdAt(chatMessage.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    public List<ChatMessageDto.Response> chatMessagesToDtos(List<ChatMessage> chatMessages) {
        return chatMessages.stream().map(
                chatMessage -> chatMessageToDto(chatMessage)
        ).collect(Collectors.toList());
    }
}
