package challenge.server.user.mapper;

import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.habit.entity.Habit;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapperImpl {
    private final BookmarkRepository bookmarkRepository;

    public User userPostDtoToUser(UserDto.Post requestBody) {
        if (requestBody == null) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email(requestBody.getEmail());
        user.password(requestBody.getPassword());
        user.username(requestBody.getUsername());

        return user.build();
    }

    public User userPatchDtoToUser(UserDto.Patch requestBody) {
        if (requestBody == null) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId(requestBody.getUserId());
        user.password(requestBody.getPassword());
        user.profileImageUrl(requestBody.getProfileImageUrl());
        user.username(requestBody.getUsername());

        return user.build();
    }

    public UserDto.SimpleResponse userToUserSimpleResponseDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto.SimpleResponse.SimpleResponseBuilder simpleResponse = UserDto.SimpleResponse.builder();

        simpleResponse.userId(user.getUserId());
        simpleResponse.email(user.getEmail());
        simpleResponse.username(user.getUsername());
        simpleResponse.profileImageUrl(user.getProfileImageUrl());

        return simpleResponse.build();
    }

    public UserDto.PatchResponse userToUserPatchResponseDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto.PatchResponse.PatchResponseBuilder patchResponse = UserDto.PatchResponse.builder();

        patchResponse.userId(user.getUserId());
        patchResponse.username(user.getUsername());
        patchResponse.profileImageUrl(user.getProfileImageUrl());

        return patchResponse.build();
    }

    public UserDto.HabitResponse habitToUserDtoHabitResponse(Habit habit) {
        if (habit == null) {
            return null;
        }

        UserDto.HabitResponse.HabitResponseBuilder habitResponse = UserDto.HabitResponse.builder();

        habitResponse.habitId(habit.getHabitId());
        habitResponse.title(habit.getTitle());
        habitResponse.subTitle(habit.getSubTitle());
        habitResponse.body(habit.getBody());
//        habitResponse.isBooked(bookmarkRepository.findByUserUserIdAndHabitHabitId(habit.get))
        habitResponse.thumbImgUrl(habit.getThumbImgUrl());

        return habitResponse.build();
    }

    public List<UserDto.HabitResponse> habitsToUserDtoHabitResponses(List<Habit> habits) {
        if (habits == null) {
            return null;
        }

        List<UserDto.HabitResponse> list = new ArrayList<UserDto.HabitResponse>(habits.size());
        for (Habit habit : habits) {
            list.add(habitToUserDtoHabitResponse(habit));
        }

        return list;
    }

    public User UserCheckPasswordDtoToUser(UserDto.CheckPassword requestBody) {
        if (requestBody == null) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId(requestBody.getUserId());
        user.password(requestBody.getPassword());

        return user.build();
    }
}
