package challenge.server.habit.entity;

import challenge.server.challenge.entity.Challenge;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgeRatio {

    @ColumnDefault(value = "0")
    int teenagerCount;
    @ColumnDefault(value = "0")
    int twentiesCount;
    @ColumnDefault(value = "0")
    int thirtiesCount;
    @ColumnDefault(value = "0")
    int fortiesCount;
    @ColumnDefault(value = "0")
    int fiftiesCount;
    @ColumnDefault(value = "0")
    int sixtiesCount;
    @ColumnDefault(value = "0")
    int seventiesCount;

        public AgeRatio makeStatistics(Habit habit) {
        if (habit != null) {
            int totalChallengeCount = habit.getChallenges().size();

            int count = 0;
            for (Challenge c3 : habit.getChallenges()) {
                int a2 = c3.getUser().getAge();
                if (10 <= a2 && a2 <= 19) {
                    count++;
                }
            }
            this.teenagerCount = count;

            int count2 = 0;
            for (Challenge challenge2 : habit.getChallenges()) {
                int i1 = challenge2.getUser().getAge();
                if (20 <= i1 && i1 <= 29) {
                    count2++;
                }
            }
            this.twentiesCount = count2;

            int count3 = 0;
            for (Challenge c2 : habit.getChallenges()) {
                int age1 = c2.getUser().getAge();
                if (30 <= age1 && age1 <= 39) {
                    count3++;
                }
            }
            this.thirtiesCount = count3;

            int count4 = 0;
            for (Challenge challenge1 : habit.getChallenges()) {
                int a1 = challenge1.getUser().getAge();
                if (40 <= a1 && a1 <= 49) {
                    count4++;
                }
            }
            this.fortiesCount = count4;

            int count5 = 0;
            for (Challenge c1 : habit.getChallenges()) {
                int i = c1.getUser().getAge();
                if (50 <= i && i <= 59) {
                    count5++;
                }
            }
            this.fiftiesCount = count5;

            int count6 = 0;
            for (Challenge challenge : habit.getChallenges()) {
                int age = challenge.getUser().getAge();
                if (60 <= age && age <= 69) {
                    count6++;
                }
            }
            this.sixtiesCount = count6;

            int count7 = 0;
            for (Challenge c : habit.getChallenges()) {
                int a = c.getUser().getAge();
                if (70 <= a && a <= 79) {
                    count7++;
                }
            }
            this.seventiesCount = count7;

//            this.teenager = (int) Math.round(teenagerCount / totalChallengeCount * 100);
//            this.twenties = (int) Math.round(twentiesCount / totalChallengeCount * 100);
//            this.thirties = (int) Math.round(thirtiesCount / totalChallengeCount * 100);
//            this.forties = (int) Math.round(fortiesCount / totalChallengeCount * 100);
//            this.fifties = (int) Math.round(fiftiesCount / totalChallengeCount * 100);
//            this.sixties = (int) Math.round(sixtiesCount / totalChallengeCount * 100);
//            this.seventies = (int) Math.round(seventiesCount / totalChallengeCount * 100);
        }
        return this;
    }
}
