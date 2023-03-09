package challenge.server.domain.chat.chatMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ChatMessageDto {

    @Getter
    @Setter
    @Builder
    public static class Request {
        private Long chatRoomId;

        @NotBlank
        private Long userId;

        @NotEmpty
        private String status;

        private String content;
    }

    @Getter
    @Setter
    @Builder
    public static class Response {
        private Long chatMessageId; // 채팅방 전체 채팅 조회 시 헷갈림 방지
        private Long userId;
        private String nickname;
        private String profileImg;
//        private String status;
        private String content;
        private String createdAt;
    }
}