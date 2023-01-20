package challenge.server.habit.mapper;

import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.category.service.CategoryService;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.habit.dto.HabitDto.Patch;
import challenge.server.habit.dto.HabitDto.Post;
import challenge.server.habit.dto.HabitDto.Response;
import challenge.server.habit.dto.HabitDto.ResponseDetail;
import challenge.server.habit.entity.Habit;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitMapperImpl {
    // MapStruct mapper로 매핑되지 않는 필드를 채우기 위함

    private final CategoryService categoryService;
    private final UserService userService;
    private final ChallengeRepository challengeRepository;
    private final BookmarkRepository bookmarkRepository;

    public Habit habitPostDtoToHabit(Post post) {
        if ( post == null ) {
            return null;
        }

        Habit habit = Habit.builder()
                .title(post.getTitle())
                .subTitle(post.getSubTitle())
                .body(post.getBody())

                .category(categoryService.findByType(post.getCategory()))
                .host(userService.findUser(post.getUserId()))

                // parse DateTimeFormatter.ISO_LOCAL_TIME 형태 - hh:mm:ss
                .authStartTime(LocalTime.parse(post.getAuthStartTime()+":00"))
                .authEndTime(LocalTime.parse(post.getAuthEndTime()+":00"))
                .build();

        return habit;
    }

    public Habit habitPatchDtoToHabit(Patch patch) {
        if ( patch == null ) {
            return null;
        }

        Habit habit = Habit.builder()
                .habitId(patch.getHabitId())
                .title(patch.getTitle())
                .subTitle(patch.getSubTitle())
                .body(patch.getBody())

                .category(categoryService.findByType(patch.getCategory()))

                .authStartTime(LocalTime.parse(patch.getAuthStartTime()+":00"))
                .authEndTime(LocalTime.parse(patch.getAuthEndTime()+":00"))
                .build();

        return habit;
    }

    public List<Response> habitsToHabitResponseDtos(List<Habit> habits) {
        if ( habits == null ) {
            return null;
        }

        List<Response> list = new ArrayList<Response>( habits.size() );
        for ( Habit habit : habits ) {
            list.add( habitToResponse( habit ) );
        }

        return list;
    }

    public ResponseDetail habitToHabitResponseDetailDto(Habit habit) {
        if ( habit == null ) {
            return null;
        }

        ResponseDetail responseDetail = ResponseDetail.builder()
                .habitId(habit.getHabitId())
                .userId(habit.getHost().getUserId())
                .category(habit.getCategory().getType())
                .title(habit.getTitle())
                .subTitle(habit.getSubTitle())
                .body(habit.getBody())
                .authStartTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthStartTime()))
                .authEndTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthEndTime()))
                .thumbImgUrl(habit.getThumbImgUrl())
                .succImgUrl(habit.getSuccImgUrl())
                .failImgUrl(habit.getFailImgUrl())
                .challengeStatus(challengeRepository
                        .findByUserUserIdAndHabitHabitId(habit.getHost().getUserId(),habit.getHabitId())
                        .get().getStatus().toString()) // 유저가 참여중인 해빗의 상태
                .isBooked(!bookmarkRepository
                        .findByUserUserIdAndHabitHabitId(habit.getHost().getUserId(), habit.getHabitId())
                        .isEmpty()) // 유저가 참여중인 해빗이 북마크 되었는지(북마크 테이블에 있는지)
                .build();

        return responseDetail;
    }

    protected Response habitToResponse(Habit habit) {
        if ( habit == null ) {
            return null;
        }

        Response response = Response.builder()
                .habitId(habit.getHabitId())
                .title(habit.getTitle())
                .body(habit.getBody())
                .thumbImgUrl(habit.getThumbImgUrl())
                .build();

        return response;
    }
}
