package challenge.server.auth.mapper;

import challenge.server.auth.dto.AuthDto;
import challenge.server.auth.entity.Auth;
import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.entity.Habit;
import challenge.server.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    Auth toEntity(AuthDto.Post postDto);

    Auth toEntity(AuthDto.Patch patchDto);

    AuthDto.Response toDto(Auth auth);

    List<AuthDto.Response> toDtos(List<Auth> auths);
}
