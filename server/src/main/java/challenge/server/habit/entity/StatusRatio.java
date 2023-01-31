package challenge.server.habit.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static challenge.server.challenge.entity.Challenge.Status.*;
import static java.lang.Math.round;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatusRatio {
    int challengeCount;
    int successCount;
    int failCount;

    public StatusRatio makeStatistics(Habit habit) {
        this.challengeCount = (int) habit.getChallenges().stream()
                .filter(challenge -> challenge.getStatus().equals(CHALLENGE)).count();
        this.successCount = (int) habit.getChallenges().stream()
                .filter(challenge -> challenge.getStatus().equals(SUCCESS)).count();
        this.failCount = (int) habit.getChallenges().stream()
                .filter(challenge -> challenge.getStatus().equals(FAIL)).count();

        return this;
    }
//    public StatusRatio makeStatistics(Habit habit) {
//        if (habit != null) {
//            int totalChallengeCount = habit.getChallenges().size();
//            double challengeCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == CHALLENGE).count();
//            double successCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == SUCCESS).count();
//            double failCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == FAIL).count();
//
//            this.challenge = (int) round(challengeCount / totalChallengeCount * 100);
//            this.success = (int) round(successCount / totalChallengeCount * 100);
//            this.fail = (int) round(failCount / totalChallengeCount * 100);
//        }
//        return this;
//    }
}
