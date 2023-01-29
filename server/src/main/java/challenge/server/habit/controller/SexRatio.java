package challenge.server.habit.controller;

import challenge.server.habit.entity.Habit;
import lombok.*;

import static challenge.server.security.user.entity.User.Gender.MALE;

//@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SexRatio {
    int male;
    int female;

    public void makeStatistics(Habit habit) {
        if (habit != null) {
            int totalChallengeCount = habit.getChallenges().size();
            double maleCount = habit.getChallenges().stream().
                    filter(challenge -> challenge.getUser().getGender() == MALE).count();
            double femaleCount = totalChallengeCount - maleCount;

            this.male = (int) Math.round(maleCount / totalChallengeCount * 100);
            this.female = (int) Math.round(femaleCount / totalChallengeCount * 100);
        }
    }
}
