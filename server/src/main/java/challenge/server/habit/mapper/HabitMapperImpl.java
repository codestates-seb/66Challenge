package challenge.server.habit.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import challenge.server.category.service.CategoryService;
import challenge.server.habit.dto.HabitDto;
import challenge.server.review.repository.ReviewRepository;
import challenge.server.user.service.UserService;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.dto.HabitDto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static challenge.server.challenge.entity.Challenge.Status.*;
import static java.lang.Math.round;

@Component
@RequiredArgsConstructor
public class HabitMapperImpl {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ChallengeRepository challengeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ReviewRepository reviewRepository;

    public Habit habitPostDtoToHabit(Post post) {
        if (post == null) {
            return null;
        }

        Habit habit = Habit.builder()
                .title(post.getTitle())
                .subTitle(post.getSubTitle())
                .body(post.getBody())
                .mdBody(post.getMdBody())

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
                .mdBody(patch.getMdBody())
                .category(categoryService.findByType(patch.getCategory()))
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
                .build();

        System.out.println(overview.getScore());
        return overview;
    }

//    private double getHabitScore(Long habitId) {
//        Double score = reviewRepository.findAverage(habitId);
//        if(score!=null) return Math.round(score * 100) / 100d;
//        else return 0d;
//    }

    protected Detail habitToDetail(Habit habit, Long userId) {
        if (habit == null) return null;
        Detail detail = Detail.builder()
                .hostUsername(habit.getHost().getUsername())
                .subTitle(habit.getSubTitle())
                .mdBody(habit.getMdBody())
                .authType(habit.getAuthType())
                .authStartTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthStartTime()).substring(0, 5))
                .authEndTime(DateTimeFormatter.ISO_LOCAL_TIME.format(habit.getAuthEndTime()).substring(0, 5))
                // challenge 테이블에서 userId(로그인한 사용자)와 habitId로 챌린지 상태 조회.
                .challengeStatus(getChallengeStatus(userId, habit.getHabitId()))
                .challengers(habit.getChallengers() == null ? 0 : habit.getChallengers())
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

    public HabitDto.ResponseStatistics makeHabitStatistics(Habit habit) {
        if (habit == null) return null;

        int totalChallengeCount = habit.getChallenges().size();
        double challengeCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == CHALLENGE).count();
        double successCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == SUCCESS).count();
        double failCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == FAIL).count();

        int challenge = (int) round(challengeCount / totalChallengeCount * 100);
        int success = (int) round(successCount / totalChallengeCount * 100);
        int fail = (int) round(failCount / totalChallengeCount * 100);

        return HabitDto.ResponseStatistics
                .builder()
                .challenge(challenge)
                .success(success)
                .fail(fail)
                .build();
    }
}
