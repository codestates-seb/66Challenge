package challenge.server.user.repository;

import challenge.server.user.dto.UserDto;

import java.util.List;

public interface UserCustomRepository {
    UserDto.UserDetailsDb findUserDetails(Long userId);

//    LocalDateTime find

    List<UserDto.ChallengeDetailsDb> findChallengeDetails(Long userId);
}
