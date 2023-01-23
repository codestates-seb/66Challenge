package challenge.server.bookmark.mapper;

import challenge.server.bookmark.dto.BookmarkDto;
import challenge.server.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    default BookmarkDto.Response bookmarkToBookmarkResponseDto(Bookmark bookmark) {
        if (bookmark == null) {
            return null;
        }

        BookmarkDto.Response.ResponseBuilder response = BookmarkDto.Response.builder();

        response.bookmarkId(bookmark.getBookmarkId());
        response.userId(bookmark.getUser().getUserId());
        response.habitId(bookmark.getHabit().getHabitId());

        return response.build();
    }
}
