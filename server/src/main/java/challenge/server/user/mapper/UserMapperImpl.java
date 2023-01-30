package challenge.server.user.mapper;

import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.habit.entity.Habit;
import challenge.server.security.dto.SecurityDto;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static challenge.server.user.entity.User.Gender.FEMALE;
import static challenge.server.user.entity.User.Gender.MALE;

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
        user.age(Integer.parseInt(requestBody.getAge()));

        if (requestBody.getGender().equals("male")) {
            user.gender(MALE);
        } else {
            user.gender(FEMALE);
        }

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
        simpleResponse.age(String.valueOf(user.getAge()));

        if (user.getGender() == MALE) {
            simpleResponse.gender("male");
        } else {
            simpleResponse.gender("female");
        }

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
        habitResponse.isBooked(true);   // 찜한 습관 조회를 한거기 때문에 true 설정(임시방편). @Mapper 구현 후 수정 필요
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

    // 2023.1.27(금) 14h45 로그아웃(+로그인) controller 구현 시 추가
    public User loginRequestDtoToUser(SecurityDto.LoginRequestDto requestBody) {
        if (requestBody == null) {
            return null;
        }

        /*
        User.UserBuilder user = User.builder();

        user.email(requestBody.getUsername());
        user.password(requestBody.getPassword());
         */
        return User.builder()
                .email(requestBody.getUsername())
                .password(requestBody.getPassword())
                .build();
    }

    public UserDto.Redis userToRedisUser(User user) {
        return UserDto.Redis.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }
}
