package challenge.server.habit.mapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import challenge.server.category.service.CategoryService;
import challenge.server.challenge.service.ChallengeService;
import challenge.server.exception.BusinessLogicException;
import challenge.server.user.service.UserService;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.challenge.entity.Challenge;

import challenge.server.habit.entity.Habit;
import challenge.server.habit.dto.HabitDto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitMapperImpl {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ChallengeRepository challengeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ChallengeService challengeService;

    public Habit habitPostDtoToHabit(Post post) {
        if (post == null) {
            return null;
        }

        Habit habit = Habit.builder()
                .title(post.getTitle())
                .subTitle(post.getSubTitle())
                .body(post.getBody())
                .bodyHtml(post.getBodyHTML())

                .category(categoryService.findByType(post.getCategory()))
                .host(userService.findUser(post.getHostUserId()))

                // parse DateTimeFormatter.ISO_LOCAL_TIME 형태 - hh:mm:ss
                .authStartTime(LocalTime.parse(post.getAuthStartTime() + ":00"))
                .authEndTime(LocalTime.parse(post.getAuthEndTime() + ":00"))
                .build();

        return habit;
    }

    public Habit habitPatchDtoToHabit(Patch patch) {
        if (patch == null) {
            return null;
        }

        Habit habit = Habit.builder()
                .title(patch.getTitle())
                .subTitle(patch.getSubTitle())
                .body(patch.getBody())
                .bodyHtml(patch.getBodyHTML())
                .authStartTime(patch.getAuthStartTime() == null ? null : LocalTime.parse(patch.getAuthStartTime() + ":00"))
                .authEndTime(patch.getAuthEndTime() == null ? null : LocalTime.parse(patch.getAuthEndTime() + ":00"))
                .authType(patch.getAuthType())
                .build();

        return habit;
    }

    public List<Overview> habitsToHabitResponseDtos(List<Habit> habits, Long userId) {
        if (habits == null) {
            return null;
        }

        List<Overview> list = new ArrayList<Overview>(habits.size());
        for (Habit habit : habits) {
            list.add(habitToOverview(habit, userId));
        }

        return list;
    }

    public ResponseDetail habitToHabitResponseDetailDto(Habit habit, Long userId) {
        if (habit == null) {
            return null;
        }

        ResponseDetail responseDetail = ResponseDetail.builder()
                .overview(habitToOverview(habit, userId))
                .detail(habitToDetail(habit, userId))
                .image(habitToImage(habit))
                .build();

        return responseDetail;
    }

    protected Overview habitToOverview(Habit habit, Long userId) {
        if (habit == null) {
            return null;
        }

        Overview overview = Overview.builder()
                .habitId(habit.getHabitId())
                .hostUserId(habit.getHost().getUserId())
                .title(habit.getTitle())
                .body(habit.getBody())
                .thumbImgUrl(habit.getThumbImgUrl())
                .score(habit.getAvgScore() == null ? 0 : habit.getAvgScore())
                // bookmark 테이블에서 userId(로그인한 사용자)와 habitId로 조회
                .isBooked(!bookmarkRepository.findByUserUserIdAndHabitHabitId(userId, habit.getHabitId()).isEmpty())
                .challengeStatus(getChallengeStatus(userId,habit.getHabitId()))
                .day(getChallengeDays(userId,habit.getHabitId()))
                .build();

        return overview;
    }

    // challenge를 며칠동안 진행했는지 리턴
    private int getChallengeDays(Long userId, Long habitId) {
        Challenge challenge;
        try {
            challenge = challengeService.findByUserIdAndHabitId(userId, habitId);
        } catch (BusinessLogicException e) {
            return 0; // challenge가 없다면 시작하지 않았음, 0 리턴
        }
        return (int) ChronoUnit.DAYS.between(challenge.getCreatedAt(),LocalDateTime.now());
    }

    protected Detail habitToDetail(Habit habit, Long userId) {
        if (habit == null) return null;
        Detail detail = Detail.builder()
                .hostUsername(habit.getHost().getUsername())
                .hostUserImgUrl(habit.getHost().getProfileImageUrl())
                .subTitle(habit.getSubTitle())
                .category(habit.getCategory().getType())
                .categoryId(habit.getCategory().getCategoryId())
                .bodyHTML(habit.getBodyHtml())
                .authType(habit.getAuthType())
                .authStartTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthStartTime()).substring(0, 5))
                .authEndTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthEndTime()).substring(0, 5))
                .challengeStatus(getChallengeStatus(userId, habit.getHabitId()))
                .challengers(challengeRepository.findChallengers(habit.getHabitId()))
                .allChallengers(challengeRepository.findAllChallengers(habit.getHabitId()))
                .build();
        return detail;
    }

    // TODO Refactor :: 챌린지 테이블에 데이터가 없다면 NONE 리턴
    private String getChallengeStatus(Long userId, Long habitId) {
        Optional<Challenge> challenge = challengeRepository.findByUserUserIdAndHabitHabitId(userId, habitId);
        if (challenge.isPresent()) return challenge.get().getStatus().toString();
        else return "NONE";
    }

    protected Image habitToImage(Habit habit) {
        if (habit == null) return null;
        Image image = Image.builder()
                .succImgUrl(habit.getSuccImgUrl())
                .failImgUrl(habit.getFailImgUrl())
                .build();
        return image;
    }

//    public HabitDto.ResponseStatistics makeHabitStatistics(Habit habit) {
//        if (habit == null) return null;
//        AgeRatio ageRatio = AgeRatio.builder().build();
//        SexRatio sexRatio = SexRatio.builder().build();
//        StatusRatio statusRatio = StatusRatio.builder().build();
//        return ResponseStatistics.builder()
//                .ageRatio(ageRatio.makeStatistics(habit))
//                .statusRatio(statusRatio.makeStatistics(habit))
//                .sexRatio(sexRatio.makeStatistics(habit))
//                .build();
//    }
}