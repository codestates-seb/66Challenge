package challenge.server.chat.chatMessage;

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

        private String createdAt;
    }

    @Getter
    @Setter
    @Builder
    public static class Response {
        private Long chatRoomId;

        private Long userId;

        private String nickname;

        private String profileImg;

        private String status;

        private String content;

        private String createdAt;
    }
}