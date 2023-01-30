package challenge.server.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NumOfAuthByChallenge {
    Long habitId;
    String habitTitle;
    LocalDateTime createdAt;
    int numOfAuth;
    int numOfUsedWildCard;
}