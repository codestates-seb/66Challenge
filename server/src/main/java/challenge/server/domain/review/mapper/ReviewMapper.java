package challenge.server.domain.review.mapper;

import challenge.server.domain.review.dto.ReviewDto;
import challenge.server.domain.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewDto.Post postDto);

    Review toEntity(ReviewDto.Patch patchDto);

    @Mapping(source = "review.user.userId", target = "reviewerUserId")
    @Mapping(source = "review.user.username", target = "reviewerUsername")
    ReviewDto.Response toDto(Review review);

    List<ReviewDto.Response> toDtos(List<Review> reviews);
}
