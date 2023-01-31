package challenge.server.habit.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import static challenge.server.user.entity.User.Gender.FEMALE;
import static challenge.server.user.entity.User.Gender.MALE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SexRatio {
    @ColumnDefault(value = "0")
    int maleCount;

    @ColumnDefault(value = "0")
    int femaleCount;

    public SexRatio makeStatistics(Habit habit) {
        this.femaleCount = (int) habit.getChallenges().stream()
                .filter(challenge -> challenge.getUser().getGender() == FEMALE).count();
        this.maleCount = (int) habit.getChallenges().stream()
                .filter(challenge -> challenge.getUser().getGender() == MALE).count();

        return this;
    }

//    public SexRatio makeStatistics(Habit habit) {
//        if (habit != null) {
//            int totalChallengeCount = habit.getChallenges().size();
//            double maleCount = habit.getChallenges().stream().
//                    filter(challenge -> challenge.getUser().getGender() == MALE).count();
//            double femaleCount = habit.getChallenges().stream().
//                    filter(challenge -> challenge.getUser().getGender() == FEMALE).count();
//
//            this.male = (int) Math.round(maleCount / totalChallengeCount * 100);
//            this.female = (int) Math.round(femaleCount / totalChallengeCount * 100);
//        }
//        return this;
//    }
}
