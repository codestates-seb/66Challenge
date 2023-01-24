package challenge.server.auth.mapper;

import challenge.server.auth.dto.AuthDto;
import challenge.server.auth.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    Auth toEntity(AuthDto.Post postDto);

    Auth toEntity(AuthDto.Patch patchDto);

    @Mapping(source = "auth.challenge.user.userId", target = "authorUserId")
    AuthDto.Response toDto(Auth auth);

    List<AuthDto.Response> toDtos(List<Auth> auths);
}
