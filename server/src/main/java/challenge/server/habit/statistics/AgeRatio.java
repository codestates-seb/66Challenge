package challenge.server.habit.statistics;

import challenge.server.habit.entity.Habit;
import lombok.*;

import javax.persistence.Embeddable;

import static challenge.server.challenge.entity.Challenge.Status.*;

//@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgeRatio {

    int teenager;
    int twenties;
    int thirties;
    int forties;
    int fifties;
    int sixties;
    int seventies;

    public void makeStatistics(Habit habit) {
        if (habit != null) {
            int totalChallengeCount = habit.getChallenges().size();
            habit.getChallenges().stream().mapToInt(c -> c.getUser().getAge())
                    .filter(a -> 20 <= a & a <= 29).count();
        }
        this.teenager = 0;
        this.twenties = 0;
        this.thirties = 0;
        this.forties = 0;
        this.fifties = 0;
        this.sixties = 0;
        this.seventies = 0;
    }
}
