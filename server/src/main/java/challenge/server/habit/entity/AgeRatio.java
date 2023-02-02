package challenge.server.habit.entity;
import challenge.server.challenge.entity.Challenge;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgeRatio {

    @ColumnDefault(value = "0")
    int teenager;
    @ColumnDefault(value = "0")
    int twenties;
    @ColumnDefault(value = "0")
    int thirties;
    @ColumnDefault(value = "0")
    int forties;
    @ColumnDefault(value = "0")
    int fifties;
    @ColumnDefault(value = "0")
    int sixties;
    @ColumnDefault(value = "0")
    int seventies;

    public AgeRatio makeStatistics(Habit habit) {
        if (habit != null) {
            int totalChallengeCount = habit.getChallenges().size();

            long count3 = 0L;
            for (Challenge c3 : habit.getChallenges()) {
                int a2 = c3.getUser().getAge();
                if (10 <= a2 && a2 <= 19) {
                    count3++;
                }
            }
            double teenagerCount = count3;

            long result2 = 0L;
            for (Challenge challenge2 : habit.getChallenges()) {
                int i1 = challenge2.getUser().getAge();
                if (20 <= i1 && i1 <= 29) {
                    result2++;
                }
            }
            double twentiesCount = result2;

            long count2 = 0L;
            for (Challenge c2 : habit.getChallenges()) {
                int age1 = c2.getUser().getAge();
                if (30 <= age1 && age1 <= 39) {
                    count2++;
                }
            }
            double thirtiesCount = count2;

            long result1 = 0L;
            for (Challenge challenge1 : habit.getChallenges()) {
                int a1 = challenge1.getUser().getAge();
                if (40 <= a1 && a1 <= 49) {
                    result1++;
                }
            }
            double fortiesCount = result1;

            long count1 = 0L;
            for (Challenge c1 : habit.getChallenges()) {
                int i = c1.getUser().getAge();
                if (50 <= i && i <= 59) {
                    count1++;
                }
            }
            double fiftiesCount = count1;

            long result = 0L;
            for (Challenge challenge : habit.getChallenges()) {
                int age = challenge.getUser().getAge();
                if (60 <= age && age <= 69) {
                    result++;
                }
            }
            double sixtiesCount = result;

            long count = 0L;
            for (Challenge c : habit.getChallenges()) {
                int a = c.getUser().getAge();
                if (70 <= a && a <= 79) {
                    count++;
                }
            }
            double seventiesCount = count;

            this.teenager = (int) Math.round(teenagerCount / totalChallengeCount * 100);
            this.twenties = (int) Math.round(twentiesCount / totalChallengeCount * 100);
            this.thirties = (int) Math.round(thirtiesCount / totalChallengeCount * 100);
            this.forties = (int) Math.round(fortiesCount / totalChallengeCount * 100);
            this.fifties = (int) Math.round(fiftiesCount / totalChallengeCount * 100);
            this.sixties = (int) Math.round(sixtiesCount / totalChallengeCount * 100);
            this.seventies = (int) Math.round(seventiesCount / totalChallengeCount * 100);
        }
        return this;
    }
}