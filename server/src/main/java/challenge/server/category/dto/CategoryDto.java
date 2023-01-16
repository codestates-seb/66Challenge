package challenge.server.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class CategoryDto {

    @Getter
    @Setter
    public static class Post {
        @ApiModelProperty(example = "자기계발")
        private String type;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {

        @ApiModelProperty(example = "1", value = "카테고리 식별자")
        private Long categoryId;
        @ApiModelProperty(example = "자기계발")
        private String type;
    }
}
