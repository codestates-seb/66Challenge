package challenge.server.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookmarkDto {
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        private Long bookmarkId;
        private Long userId;
        private Long habitId;
    }
}
