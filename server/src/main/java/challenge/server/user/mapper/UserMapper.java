package challenge.server.user.mapper;

import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userPostDtoToUser(UserDto.Post requestBody);

    UserDto.SimpleResponse userToUserSimpleResponseDto(User user);

    UserDto.DetailResponse userToUserDetailResponseDto(User user);
}
