package challenge.server.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        private Long categoryId;
        private String type;
    }
}
