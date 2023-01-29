package challenge.server.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userStatisticsId;

//    private int numOfChallenges; // 내가 참여중인 습관 갯수(프론트)
//    private int numOfBookmarks; // 찜한 습관 갯수(프론트)
//    private int numOfHostHabits; // 만든 습관 갯수(프론트)

    // 내가 현재까지 올린 인증 글의 갯수 -> todo 습관별? 전체?
    private int numOfAuthByHabit = 0;




}
