package challenge.server.habit.controller;

import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.dto.HabitDto;
import challenge.server.habit.entity.Habit;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Embeddable;

import static challenge.server.challenge.entity.Challenge.Status.*;
import static java.lang.Math.round;

@Slf4j
@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatusRatio {
    int challenge;
    int success;
    int fail;

    public void makeStatistics(Habit habit) {
        if (habit != null) {
            int totalChallengeCount = habit.getChallenges().size();
            double challengeCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == CHALLENGE).count();
            double successCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == SUCCESS).count();
            double failCount = (double) habit.getChallenges().stream().filter(c -> c.getStatus() == FAIL).count();

            this.challenge = (int) round(challengeCount / totalChallengeCount * 100);
            this.success = (int) round(successCount / totalChallengeCount * 100);
            this.fail = (int) round(failCount / totalChallengeCount * 100);
        }
    }
}
