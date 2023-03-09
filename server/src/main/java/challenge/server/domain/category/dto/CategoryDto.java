package challenge.server.domain.category.dto;

import lombok.*;

public class CategoryDto {

    // TODO 관리자 페이지 구현 시 PostDto, PatchDto 활성화
    @Getter
    @Setter
    public static class Post {
        private String type;
    }

    @Getter
    @Setter
    public static class Patch {
        private Long categoryId;
        private String type;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        private Long categoryId;
        private String type;
    }
}
