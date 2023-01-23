package challenge.server.user.mapper;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.habit.entity.Habit;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userPostDtoToUser(UserDto.Post requestBody);

    User userPatchDtoToUser(UserDto.Patch requestBody);

    UserDto.SimpleResponse userToUserSimpleResponseDto(User user);

    UserDto.PatchResponse userToUserPatchResponseDto(User user);

    /*
    default UserDto.DetailResponse userToUserDetailResponseDto(User user) {
//        return UserDto.DetailResponse.builder()
//                .userId(user.getUserId())
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .biggestNumOfChallengeHabitDays(LocalDateTime.now() - user.getChallenges().stream()
//                        .filter(challenge -> challenge.getStatus().equals("CHALLENGE"))
//                        .map(challenge -> challenge.getCreatedAt())
//                        .min().get())

        return null;
    }

    default UserDto.DetailResponse detailsResultsToUserDetailResponseDto(List<Object> detailsResults) {
        return null;
    }
     */

    default List<UserDto.HabitResponse> habitsToUserDtoHabitResponses(List<Habit> habits) {
        return null;
    }

    User UserCheckPasswordDtoToUser(UserDto.CheckPassword requestBody);
}
