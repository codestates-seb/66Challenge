package challenge.server.security.user.repository;

import challenge.server.security.user.dto.UserDto;

import java.util.List;

public interface UserCustomRepository {
    UserDto.UserDetailsDb findUserDetails(Long userId);

//    LocalDateTime find

    List<UserDto.ChallengeDetailsDb> findChallengeDetails(Long userId);
}
