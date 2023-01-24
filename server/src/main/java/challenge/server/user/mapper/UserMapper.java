package challenge.server.user.mapper;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.bookmark.repository.BookmarkRepository;
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

    /* 아래 dto 대신 UserDto.UserDetailsDb를 service에서 직접 만들어서 반환 -> 마이페이지 조회 관련 mapper 필요 없음
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

    UserDto.HabitResponse habitToUserDtoHabitResponse(Habit habit);

    List<UserDto.HabitResponse> habitsToUserDtoHabitResponses(List<Habit> habits);

    User UserCheckPasswordDtoToUser(UserDto.CheckPassword requestBody);
}
