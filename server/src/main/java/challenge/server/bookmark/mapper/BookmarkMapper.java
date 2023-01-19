package challenge.server.bookmark.mapper;

import challenge.server.bookmark.dto.BookmarkDto;
import challenge.server.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    BookmarkDto.Response bookmarkToBookmarkResponseDto(Bookmark bookmark);
}
