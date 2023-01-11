package challenge.server.user.mapper;

import challenge.server.user.dto.UserDto.Post;
import challenge.server.user.dto.UserDto.Response;
import challenge.server.user.entity.User;
import challenge.server.user.entity.User.UserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-11T08:35:30+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userPostDtoToUser(Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.email( requestBody.getEmail() );
        user.password( requestBody.getPassword() );
        user.username( requestBody.getUsername() );

        return user.build();
    }

    @Override
    public Response userToUserResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long userId = null;
        String email = null;
        String username = null;

        userId = user.getUserId();
        email = user.getEmail();
        username = user.getUsername();

        Response response = new Response( userId, email, username );

        return response;
    }
}
