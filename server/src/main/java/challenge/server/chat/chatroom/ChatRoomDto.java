package challenge.server.chat.chatroom;

import challenge.server.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ChatRoomDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @JsonProperty("title")
        private String title;
        @JsonProperty("participants")
        private List<Long> participants;
    }

    @Getter
    @Setter
    @Builder // 채팅방 상세 보기
    public static class ResponseDetail {
        private Long chatRoomId;
        private Long habitId;
        private String title;
        private List<UserDto.SimpleResponse> participants;
//      private List<ChatMessageDto> chats; -> chatResponse 으로 요청 분할
    }

    @Getter
    @Setter
    @Builder // 채팅방 목록 보기
    public static class ResponseSimple {
        private Long chatRoomId;
        private Long habitId;
        private String title;
        private List<UserDto.SimpleResponse> participants;
        private String lastChat;
        private LocalDateTime lastChatAt;
    }
}
