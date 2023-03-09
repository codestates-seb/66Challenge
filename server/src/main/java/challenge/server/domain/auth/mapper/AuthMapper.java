package challenge.server.domain.auth.mapper;

import challenge.server.domain.auth.dto.AuthDto;
import challenge.server.domain.auth.entity.Auth;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    Auth toEntity(AuthDto.Post postDto);

    Auth toEntity(AuthDto.Patch patchDto);

    AuthDto.Response toDto(Auth auth);

    List<AuthDto.Response> toDtos(List<Auth> auths);
}
