package challenge.server.review.mapper;

import challenge.server.review.dto.ReviewDto;
import challenge.server.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewDto.Post postDto);

    Review toEntity(ReviewDto.Patch patchDto);

    @Mapping(source = "review.user.userId", target = "reviewerUserId")
    ReviewDto.Response toDto(Review review);

    List<ReviewDto.Response> toDtos(List<Review> reviews);
}
