package challenge.server.habit.mapper;

import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.category.service.CategoryService;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.habit.dto.HabitDto.*;
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
                .host(userService.findUser(post.getHostUserId()))

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
                .title(patch.getTitle())
                .subTitle(patch.getSubTitle())
                .body(patch.getBody())

                .category(categoryService.findByType(patch.getCategory()))

                .authStartTime(LocalTime.parse(patch.getAuthStartTime()+":00"))
                .authEndTime(LocalTime.parse(patch.getAuthEndTime()+":00"))
                .build();

        return habit;
    }

    public List<Overview> habitsToHabitResponseDtos(List<Habit> habits) {
        if ( habits == null ) {
            return null;
        }

        List<Overview> list = new ArrayList<Overview>( habits.size() );
        for ( Habit habit : habits ) {
            list.add( habitToOverview( habit ) );
        }

        return list;
    }

    public ResponseDetail habitToHabitResponseDetailDto(Habit habit) {
        if ( habit == null ) {
            return null;
        }

        ResponseDetail responseDetail = ResponseDetail.builder()
                .overview(habitToOverview(habit))
                .detail(habitToDetail(habit))
                .image(habitToImage(habit))
                .build();

        return responseDetail;
    }

    protected Overview habitToOverview(Habit habit) {
        if ( habit == null ) {
            return null;
        }

        Overview overview = Overview.builder()
                .habitId(habit.getHabitId())
                .title(habit.getTitle())
                .body(habit.getBody())
                .isBooked(!bookmarkRepository
                        .findByUserUserIdAndHabitHabitId(habit.getHost().getUserId(), habit.getHabitId())
                        .isEmpty()) // bookmark 테이블에서 userId와 habitId로 조회
                .thumbImgUrl(habit.getThumbImgUrl())
// TODO         .score("reviewRepository에서 haibitId로 조회 & score 칼럼을 통계")
                .build();

        return overview;
    }

    protected Detail habitToDetail(Habit habit) {
        if(habit==null) return null;
        Detail detail = Detail.builder()
                .hostUserId(habit.getHost().getUserId())
                .subTitle(habit.getSubTitle())
                .authType(habit.getAuthType())
                .authStartTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthStartTime()).substring(0,5))
                .authEndTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthEndTime()).substring(0,5))
                .challengeStatus(challengeRepository.findByUserUserIdAndHabitHabitId(habit.getHost().getUserId(), habit.getHabitId())
                        .get().getStatus().toString()) // challenge 테이블에서 userId와 habitId로 챌린지 상태 조회.
                .build();
        return detail;
    }

    protected Image habitToImage(Habit habit) {
        if(habit==null) return null;
        Image image = Image.builder()
                .succImgUrl(habit.getSuccImgUrl())
                .failImgUrl(habit.getFailImgUrl())
                .build();
        return image;
    }
}
